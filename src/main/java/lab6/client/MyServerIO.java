package lab6.client;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lab6.client.commands.Command;
import lab6.client.interfaces.ConnectionConfiguration;
import lab6.client.interfaces.ServerIO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

@Singleton
public class MyServerIO implements ServerIO {
    private SocketChannel socketChannel;
    private ConnectionConfiguration connectionConfiguration;
    private int noDataWrittenLimit = 512;
    private ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

    @Inject
    public MyServerIO(ConnectionConfiguration config) {
        this.connectionConfiguration = config;
    }

    public void open() throws IOException {
        socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(
                connectionConfiguration.getHost(),
                connectionConfiguration.getPort()
        ));
        socketChannel.configureBlocking(true);
    }

    public ByteBuffer getByteBuffer() {
        if (byteBuffer == null) {
            byteBuffer = ByteBuffer.allocate(1024);
        }

        return byteBuffer;
    }

    public ByteBuffer getByteBuffer(int capacity) {
        if (byteBuffer == null || byteBuffer.capacity() != capacity) {
            byteBuffer = ByteBuffer.allocate(capacity);
        }

        return byteBuffer;
    }


    @Override
    public void sendToServer(Command command) throws IOException {
        System.out.println("Команда готовится к отправке: " + command.getClass().getSimpleName());
        ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(byteArrayStream);
        objectStream.writeObject(command);
        byte[] byteArray = byteArrayStream.toByteArray();
        ByteBuffer buffer = this.getByteBuffer(byteArray.length);
        buffer.clear();

        buffer.put(byteArray);
        buffer.flip();
        while(buffer.hasRemaining()) {
            socketChannel.write(buffer);
        }
        System.out.println("Команда отправлена");
    }

    public String receiveFromServer() throws IOException {
        ByteBuffer buffer = getByteBuffer();
        buffer.clear();
        StringBuilder stringBuilder = new StringBuilder();
        byte[] readBytes;
        while (socketChannel.read(buffer) > 0) {
            buffer.flip();
            readBytes = new byte[buffer.limit()];
            buffer.get(readBytes);
            stringBuilder.append(new String(readBytes));
            buffer.clear();
        }
        return stringBuilder.toString();
    }
}
