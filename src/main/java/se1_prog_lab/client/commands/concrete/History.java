package se1_prog_lab.client.commands.concrete;

import se1_prog_lab.client.commands.ClientSideCommand;
import se1_prog_lab.client.interfaces.ClientCommandReceiver;

public class History extends ClientSideCommand {
    public History() {
        super("history", " - вывести последние 13 команд без их аргументов");
    }

    @Override
    public boolean clientExecute(String[] args, ClientCommandReceiver clientReceiver) {
        clientReceiver.history();
        return true;
    }
}
