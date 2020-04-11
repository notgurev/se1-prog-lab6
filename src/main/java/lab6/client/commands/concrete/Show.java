package lab6.client.commands.concrete;

import lab6.client.commands.RegularCommand;
import lab6.client.interfaces.ClientCommandReceiver;

public class Show extends RegularCommand {
    public Show() {
        super("show", " - вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
    }

    @Override
    public void execute(ClientCommandReceiver serverReceiver, String[] args) {
        serverReceiver.show();
    }

    @Override
    public void clientExecute(String[] args, ClientCommandReceiver clientReceiver) {
        // empty
    }
}
