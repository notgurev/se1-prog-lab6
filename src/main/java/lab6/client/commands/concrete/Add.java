package lab6.client.commands.concrete;

import lab6.client.commands.ConstructingCommand;
import lab6.client.interfaces.ClientCommandReceiver;
import lab6.server.interfaces.ServerCommandReceiver;
import lab6.util.ElementCreator;

public class Add extends ConstructingCommand {
    public Add() {
        super("add", " - добавить новый элемент в коллекцию");
    }


    @Override
    public void serverExecute(ServerCommandReceiver serverReceiver, String[] args) {
        serverReceiver.add(carriedObject);
    }

    @Override
    public void clientExecute(String[] args, ClientCommandReceiver clientCommandReceiver) {
        carriedObject = ElementCreator.buildLabWork(clientCommandReceiver);
    }
}
