package lab6.client.commands.concrete;

import lab6.client.commands.NonValidatingRegularCommand;
import lab6.client.interfaces.ClientCommandReceiver;

public class Clear extends NonValidatingRegularCommand {
    public Clear() {
        super("clear", " - очистить коллекцию");
    }

    @Override
    public void execute(ClientCommandReceiver serverReceiver, String[] args) {
        serverReceiver.clear();
    }
}
