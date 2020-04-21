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
    private ServerCommandReceiver serverCommandReceiver;
    private ServerSocket serverSocket;
    @Inject
    private ServerCommandReceiverFactory serverCommandReceiverFactory;
    @Inject
    private EOTWrapper eotWrapper;
    private static final Logger logger;
    private final int PORT = 6006;

    @Inject
    public ServerApp(CollectionWrapper collectionWrapper, ResponseBuilder responseBuilder) {
        this.collectionWrapper = collectionWrapper;
        this.responseBuilder = responseBuilder;
    }

    static {
        try {
            LogManager.getLogManager()
                    .readConfiguration(ServerApp.class.getClassLoader().getResourceAsStream("logger.properties"));
        } catch (NullPointerException | IOException e) {
            System.out.println("Не удалось загрузить конфиг логгера. Запуск в дефолтном режиме");
        }
        logger = Logger.getLogger(ServerApp.class.getName());
    }

    public static void main(String[] args) {
        logger.info("Инициализация сервера");

        if (args.length == 0 || args[0].trim().length() == 0) {
            logger.info("Не указано название файла для загрузки и сохранения коллекции!");
            System.exit(1);
        }

        Injector injector = Guice.createInjector(new ServerModule());
        Server serverApp = injector.getInstance(Server.class);

        try {
            serverApp.start(args[0]);
        } catch (IOException e) {
            logger.info("С сервером что-то пошло не так:\n" + e.getMessage());
        }
    }

    @Override
    public void start(String fileName) throws IOException {
        this.serverCommandReceiver = createServerCommandReceiver(fileName);
        this.serverSocket = new ServerSocket(PORT);
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
