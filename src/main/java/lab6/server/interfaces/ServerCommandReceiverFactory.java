package lab6.server.interfaces;

public interface ServerCommandReceiverFactory {
    ServerCommandReceiver create(String collectionFile);
}
