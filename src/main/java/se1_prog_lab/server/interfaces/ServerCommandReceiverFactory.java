package se1_prog_lab.server.interfaces;

public interface ServerCommandReceiverFactory {
    ServerCommandReceiver create(String collectionFile);
}
