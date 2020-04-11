package lab6.server;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import lab6.server.interfaces.CollectionWrapper;
import lab6.server.interfaces.Server;
import lab6.server.interfaces.ServerCommandReceiver;
import lab6.util.FileIO;

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
    }
}
