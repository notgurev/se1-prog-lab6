package lab6.client.commands.concrete;

import lab6.client.commands.NonValidatingRegularCommand;
import lab6.client.interfaces.ClientCommandReceiver;

public class Sort extends NonValidatingRegularCommand {
    public Sort() {
        super("sort", " - отсортировать коллекцию в естественном порядке");
    }

    @Override
    public void execute(ClientCommandReceiver serverReceiver, String[] args) {
        serverReceiver.sort();
    }
}
