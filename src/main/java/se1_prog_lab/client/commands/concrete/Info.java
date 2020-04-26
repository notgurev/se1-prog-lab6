package se1_prog_lab.client.commands.concrete;

import se1_prog_lab.client.commands.NonValidatingRegularCommand;
import se1_prog_lab.server.interfaces.ServerCommandReceiver;

public class Info extends NonValidatingRegularCommand {
    public Info() {
        super("info", " - вывести информацию о коллекции");
    }

    @Override
    public void serverExecute(ServerCommandReceiver serverReceiver) {
        serverReceiver.info();
    }
}
