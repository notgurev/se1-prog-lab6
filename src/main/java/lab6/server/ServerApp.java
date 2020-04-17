package lab6.server;

import com.google.inject.*;
import com.google.inject.name.Named;
import lab6.client.commands.Command;
import lab6.server.interfaces.*;
import lab6.util.FileIO;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.LogManager;
import java.util.logging.Logger;

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
    private static Logger logger = Logger.getLogger(ServerApp.class.getName());

    @Inject
    public ServerApp(CollectionWrapper collectionWrapper,
                     ServerConfiguration serverConfig,
                     ResponseBuilder responseBuilder) {
        this.serverConfiguration = serverConfig;
        this.collectionWrapper = collectionWrapper;
        this.responseBuilder = responseBuilder;
    }

    public static void main(String[] args) {
//        try {
//            LogManager.getLogManager().readConfiguration(ServerApp.class.getResourceAsStream("/logger.properties"));
//        } catch (IOException e) {
//            System.out.println("Не удалось настроить логгер.");
//        } // TODO вот такая вот загрузка, иначе нужно указать аргумент Djava.util.logging.config.file=logger.properties
        logger.info("Инициализация сервера");
        if (args.length == 0 || args[0].trim().length() == 0) {
            logger.info("Не указано название файла для загрузки и сохранения коллекции!");
            System.exit(1);
        }
        String fileName = args[0];
        Injector injector = Guice.createInjector(new ServerModule());

        Server serverApp = injector.getInstance(Server.class);
        try {
            serverApp.start(fileName);
        } catch (IOException e) {
            logger.info("С сервером что-то пошло не так:\n" + e.getMessage());
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
        logger.info("Отправка ответа клиенту");
        clientWriter.write(eotWrapper.wrap(responseBuilder.getResponse()));
        clientWriter.flush();
        responseBuilder.clearResponse();
    }

    private void handleRequests() {
        logger.info("Начата обработка запросов");
        while (true) {
            try (Socket clientSocket = serverSocket.accept();
                 BufferedWriter clientWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {
                logger.info("Принят clientSocket, создан clientWriter");

                InputStream clientInputStream = clientSocket.getInputStream();
                ObjectInputStream objectInput;
                Command command;

                while (true) {
                    objectInput = new ObjectInputStream(clientInputStream);
                    logger.info("Принят объект " + objectInput.getClass().getSimpleName());
                    command = (Command) objectInput.readObject();
                    logger.info("Начинается выполнение команды " + command.getClass().getSimpleName());
                    command.serverExecute(serverCommandReceiver);
                    logger.info("Команда выполнена");
                    sendResponseToClient(clientWriter);
                    logger.info("Отправлен ответ на команду " + command.getClass().getSimpleName());
                }
            } catch (IOException e) {
                logger.severe("Не удалось обработать запрос: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                logger.severe("Не удалось десериализировать объект: {}" + e.getMessage());
            }
        }
    }
}
