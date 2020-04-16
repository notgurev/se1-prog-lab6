package lab6.server;

import com.google.inject.*;
import com.google.inject.name.Named;
import lab6.client.commands.Command;
import lab6.server.interfaces.*;
import lab6.util.FileIO;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Singleton
public class ServerApp implements Server {
    private final CollectionWrapper collectionWrapper;
    private final ResponseBuilder responseBuilder;
    private final ServerConfiguration serverConfiguration;
    private ServerCommandReceiver serverCommandReceiver;
    private ServerSocket serverSocket;
    @Inject
    private ServerCommandReceiverFactory serverCommandReceiverFactory;
    @Inject
    private EOTWrapper eotWrapper;

    @Inject
    public ServerApp(CollectionWrapper collectionWrapper,
                     ServerConfiguration serverConfig,
                     ResponseBuilder responseBuilder) {
        this.serverConfiguration = serverConfig;
        this.collectionWrapper = collectionWrapper;
        this.responseBuilder = responseBuilder;
    }

    public static void main(String[] args) {
        if (args.length == 0 || args[0].trim().length() == 0) {
            System.out.println("Вы не ввели название файла для загрузки и сохранения коллекции!");
            System.exit(1);
        }
        String fileName = args[0];
        Injector injector = Guice.createInjector(new ServerModule());

        Server serverApp = injector.getInstance(Server.class);
        try {
            serverApp.start(fileName);
        } catch (IOException e) {
            System.out.println("С сервером что-то пошло не так:\n" + e.getMessage());
        }
    }

    @Override
    public void start(String fileName) throws IOException {
        this.serverCommandReceiver = createServerCommandReceiver(fileName);
        this.serverSocket = new ServerSocket(serverConfiguration.getPort());
        FileIO.readCollectionFromFile(fileName, collectionWrapper);
        handleRequests();
    }

    @Provides
    @Singleton
    @Named("serverCommandReceiverImpl")
    public ServerCommandReceiver createServerCommandReceiver(String fileName) {
        return serverCommandReceiverFactory.create(fileName);
    }

    private void sendResponseToClient(BufferedWriter clientWriter) throws IOException {
        clientWriter.write(eotWrapper.wrap(responseBuilder.getResponse()));
        clientWriter.flush();
        responseBuilder.clearResponse();
    }

    private void handleRequests() {
        while (true) {
            try (Socket clientSocket = serverSocket.accept();
                 BufferedWriter clientWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

                Command command;
                InputStream clientInputStream = clientSocket.getInputStream();
                ObjectInputStream objectInput;

                while (true) {
                    objectInput = new ObjectInputStream(clientInputStream);
                    command = (Command) objectInput.readObject();
                    command.serverExecute(serverCommandReceiver);
                    System.out.println("Принята команда: " + command.getClass().getSimpleName());
                    sendResponseToClient(clientWriter);
                    System.out.println("Отправлен ответ на команду " + command.getClass().getSimpleName());
                }
            } catch (IOException e) {
                System.out.println("Не удалось обработать запрос: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                System.out.println("Не удалось десериализировать объект: {}" + e.getMessage());
            }
        }
    }
}
