package lab6.client.commands.concrete;

import lab6.client.commands.RegularCommand;
import lab6.client.interfaces.ClientCommandReceiver;

public class Sort extends RegularCommand {
    public Sort() {
        super("sort", " - отсортировать коллекцию в естественном порядке");
    }

    @Override
    public void execute(ClientCommandReceiver serverReceiver, String[] args) {
        serverReceiver.sort();
    }

    @Override
    public void clientExecute(String[] args, ClientCommandReceiver clientReceiver) {
        // empty
    }
}
