package lab6.client.commands.concrete;

import lab6.client.commands.ClientSideCommand;
import lab6.client.interfaces.ClientCommandReceiver;

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
