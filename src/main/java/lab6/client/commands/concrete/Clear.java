package lab6.client.commands.concrete;

import lab6.client.commands.RegularCommand;
import lab6.client.interfaces.ClientCommandReceiver;

public class Clear extends RegularCommand {
    public Clear() {
        super("clear", " - очистить коллекцию");
    }

    @Override
    public void execute(ClientCommandReceiver serverReceiver, String[] args) {
        serverReceiver.clear();
    }

    @Override
    public void clientExecute(String[] args, ClientCommandReceiver clientReceiver) {
        // empty
    }
}
