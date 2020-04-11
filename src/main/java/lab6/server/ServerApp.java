package lab6.server;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import lab6.client.commands.Command;
import lab6.client.commands.concrete.Show;
import lab6.server.interfaces.CollectionWrapper;
import lab6.server.interfaces.Server;
import lab6.server.interfaces.ServerCommandReceiver;
import lab6.util.FileIO;

@Singleton
public class ServerApp implements Server {
    CollectionWrapper collectionWrapper;
    ServerCommandReceiver serverCommandReceiver;

    @Inject
    public ServerApp(CollectionWrapper collectionWrapper, ServerCommandReceiver serverCommandReceiver) {
        this.collectionWrapper = collectionWrapper;
        this.serverCommandReceiver = serverCommandReceiver;
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
        while (true) {
            // Принимаем запрос от сервера
            // Делаем что-то такое
            Command testCommand = new Show(); // Вместо штуки слева должна быть команда полученная
            testCommand.serverExecute(serverCommandReceiver);
            /*
                По идее MyClientIO должен теперь весь результат работы проги складывать в стринг, потом отправлять
                сразу весь клиенту. У меня там метод printToClient, что не то. Надо реализовать нормально короче.
             */
            // Отправляем собранный стринг клиенту
            // И по новой.
        }
    }
}
