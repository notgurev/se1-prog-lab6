package lab6.server.di.factories;

import lab6.server.interfaces.ServerCommandReceiver;

public interface ServerCommandReceiverFactory {
  ServerCommandReceiver create(String collectionFile);
}
