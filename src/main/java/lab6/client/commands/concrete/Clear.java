package lab6.client.commands.concrete;

import lab6.client.commands.NonValidatingRegularCommand;
import lab6.server.interfaces.ServerCommandReceiver;

public class Clear extends NonValidatingRegularCommand {
    public Clear() {
        super("clear", " - очистить коллекцию");
    }

    @Override
    public void serverExecute(ServerCommandReceiver serverReceiver) {
        serverReceiver.clear();
    }
}
