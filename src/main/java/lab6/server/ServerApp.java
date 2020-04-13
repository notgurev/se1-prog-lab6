package lab6.server;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import lab6.client.commands.Command;
import lab6.server.interfaces.CollectionWrapper;
import lab6.server.interfaces.ResponseBuilder;
import lab6.server.interfaces.Server;
import lab6.server.interfaces.ServerCommandReceiver;
import lab6.util.FileIO;

@Singleton
public class ServerApp implements Server {
    final CollectionWrapper collectionWrapper;
    final ServerCommandReceiver serverCommandReceiver;
    final ResponseBuilder responseBuilder;

    @Inject
    public ServerApp(CollectionWrapper collectionWrapper, ServerCommandReceiver serverCommandReceiver, ResponseBuilder responseBuilder) {
        this.collectionWrapper = collectionWrapper;
        this.serverCommandReceiver = serverCommandReceiver;
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
        serverApp.start(fileName);
    }


    @Override
    public void start(String fileName) {
        serverCommandReceiver.setCollectionFile(fileName);
        FileIO.readCollectionFromFile(fileName, collectionWrapper);
        Command acceptedCommand;
        while (true) {
            // Принимаем запрос от клиента
            acceptedCommand = null; // поставил нулл пока, тут получаем от клиента команду
            // Выполняем ее
            acceptedCommand.serverExecute(serverCommandReceiver);
            // там внутри addLine встречается, они добавляют в ResponseBuilder строки
            // После выполнения получаем строку и отправляем на клиент
            sendResponseToClient(responseBuilder.getResponse());
        }
    }

    void sendResponseToClient(String response) {
        // Тут отправляем клиенту ответ
    }
}
