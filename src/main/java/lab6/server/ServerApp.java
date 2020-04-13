package lab6.server;

import com.google.inject.*;
import com.google.inject.name.Named;
import lab6.client.commands.Command;
import lab6.server.di.factories.ServerCommandReceiverFactory;
import lab6.server.interfaces.CollectionWrapper;
import lab6.server.interfaces.ResponseBuilder;
import lab6.server.interfaces.Server;
import lab6.server.interfaces.ServerCommandReceiver;
import lab6.server.interfaces.ServerConfiguration;
import lab6.util.FileIO;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

@Singleton
public class ServerApp implements Server {
    private CollectionWrapper collectionWrapper;
    private ServerCommandReceiver serverCommandReceiver;
    private ResponseBuilder responseBuilder;
    private ServerConfiguration serverConfiguration;
    private ServerSocket serverSocket;
    @Inject
    private ServerCommandReceiverFactory serverCommandReceiverFactory;

    @Inject
    public ServerApp(CollectionWrapper collectionWrapper, ServerConfiguration serverConfig,
                     ResponseBuilder responseBuilder) {
        this.serverConfiguration = serverConfig;
        this.collectionWrapper = collectionWrapper;
        this.responseBuilder = responseBuilder;
    }

    public static void main(String[] args) {
        if (args.length == 0 || args[0].trim().length() == 0) {
            System.out.println("Вы не ввели название файла, из которого нужно загрузить коллекцию!");
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

    void sendResponseToClient(String response, BufferedWriter clientWriter) throws IOException {
        clientWriter.write(response);
        clientWriter.flush();
    }

    public void handleRequests() {
        while (true) {
            try (Socket clientSocket = serverSocket.accept(); ObjectInputStream objectInput = new ObjectInputStream(clientSocket.getInputStream());
                 BufferedWriter clientWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {
                Command command = (Command) objectInput.readObject();
                command.serverExecute(serverCommandReceiver);

                sendResponseToClient(responseBuilder.getResponse(), clientWriter);

                System.out.println("A command has been received");
            } catch (IOException e) {
                System.out.println("Can not handle request, the reason of that: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                System.out.println("Can not deserialize a requested command: {}" + e.getMessage());
            }
        }
    }
}
