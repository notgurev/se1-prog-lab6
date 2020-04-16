package lab6.client;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lab6.client.commands.Command;
import lab6.client.interfaces.ConnectionConfiguration;
import lab6.client.interfaces.ServerIO;
import lab6.server.interfaces.EOTWrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import static lab6.util.BetterStrings.coloredRed;
import static lab6.util.BetterStrings.coloredYellow;

@Singleton
public class MyServerIO implements ServerIO {
    private final int DEFAULT_BUFFER_CAPACITY = 1024;
    private final ConnectionConfiguration connectionConfiguration;
    private SocketChannel socketChannel;
    private ByteBuffer byteBuffer = ByteBuffer.allocate(DEFAULT_BUFFER_CAPACITY);
    @Inject
    private EOTWrapper eotWrapper;

    @Inject
    public MyServerIO(ConnectionConfiguration config) {
        this.connectionConfiguration = config;
    }

    public void open() {
        System.out.println("Попытка соединиться с сервером...");
        try {
            socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress(
                    connectionConfiguration.getHost(),
                    connectionConfiguration.getPort()
            ));
            socketChannel.configureBlocking(false);
            System.out.println(coloredYellow("Соединение с сервером успешно установлено"));
        } catch (IOException e) {
            System.out.println(coloredRed("Не получилось открыть соединение: " + e.getMessage()));
        }
    }

    private boolean isOpen() {
        return socketChannel.isOpen();
    }

    private ByteBuffer getByteBuffer() {
        if (byteBuffer == null) {
            byteBuffer = ByteBuffer.allocate(DEFAULT_BUFFER_CAPACITY);
        }
        return byteBuffer;
    }

    private ByteBuffer getByteBuffer(int capacity) {
        if (byteBuffer == null || byteBuffer.capacity() != capacity) {
            byteBuffer = ByteBuffer.allocate(capacity);
        }

        return byteBuffer;
    }

    private void sendToServer(Command command) throws IOException {
        ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(byteArrayStream);

        System.out.println("Команда готовится к отправке: " + command.getClass().getSimpleName());
        objectStream.writeObject(command);
        byte[] byteArray = byteArrayStream.toByteArray();
        ByteBuffer buffer = getByteBuffer(byteArray.length);
        buffer.clear();

        buffer.put(byteArray);
        buffer.flip();
        while (buffer.hasRemaining()) {
            socketChannel.write(buffer);
        }
        System.out.println("Команда отправлена");
    }

    private String receiveFromServer() throws IOException {
        ByteBuffer buffer = getByteBuffer();
        buffer.clear();
        StringBuilder stringBuilder = new StringBuilder();
        byte[] readBytes;
        while (!eotWrapper.hasEOTSymbol(stringBuilder.toString())) {
            if (socketChannel.read(buffer) > 0) {
                buffer.flip();
                readBytes = new byte[buffer.limit()];
                buffer.get(readBytes);
                stringBuilder.append(new String(readBytes));
                buffer.clear();
            }
        }

        return eotWrapper.unwrap(stringBuilder.toString());
    }

    @Override
    public void sendAndReceive(Command command) {
        if (command.isServerSide()) {
            try {
                if (!isOpen()) open();
                sendToServer(command);

                try {
                    String result = receiveFromServer();
                    System.out.println("Получен результат команды:\n" + result);
                } catch (IOException e) {
                    System.out.println(coloredRed("При получении ответа возникла ошибка: " + e.getMessage()));
                }

            } catch (IOException e) {
                System.out.println(coloredRed("Не получилось отправить команду: " + e.getMessage()));
                open();
                System.out.println("Повторная отправка команды " + command.getClass().getSimpleName());
                sendAndReceive(command);
            }
        }
    }
}
