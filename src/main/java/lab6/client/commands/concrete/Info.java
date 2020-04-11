package lab6.client.commands.concrete;

import lab6.client.commands.NonValidatingRegularCommand;
import lab6.server.interfaces.ServerCommandReceiver;

public class Info extends NonValidatingRegularCommand {
    public Info() {
        super("info", " - вывести информацию о коллекции");
    }

    @Override
    public void serverExecute(ServerCommandReceiver serverReceiver, String[] args) {
        serverReceiver.info();
    }
}
