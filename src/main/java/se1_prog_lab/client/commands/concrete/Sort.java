package se1_prog_lab.client.commands.concrete;

import se1_prog_lab.client.commands.NonValidatingRegularCommand;
import se1_prog_lab.server.interfaces.ServerCommandReceiver;

public class Sort extends NonValidatingRegularCommand {
    public Sort() {
        super("sort", " - отсортировать коллекцию в естественном порядке");
    }

    @Override
    public void serverExecute(ServerCommandReceiver serverReceiver) {
        serverReceiver.sort();
    }
}
