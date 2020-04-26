package se1_prog_lab.client.commands.concrete;

import se1_prog_lab.client.commands.ConstructingCommand;
import se1_prog_lab.client.interfaces.ClientCommandReceiver;
import se1_prog_lab.server.interfaces.ServerCommandReceiver;
import se1_prog_lab.util.ElementCreator;

public class Add extends ConstructingCommand {
    public Add() {
        super("add", " - добавить новый элемент в коллекцию");
    }


    @Override
    public void serverExecute(ServerCommandReceiver serverReceiver) {
        serverReceiver.add(carriedObject);
    }

    @Override
    public boolean clientExecute(String[] args, ClientCommandReceiver clientCommandReceiver) {
        carriedObject = ElementCreator.buildLabWork(clientCommandReceiver);
        return true;
    }
}
