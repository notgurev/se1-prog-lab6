package lab6.client.commands.concrete;

import lab6.client.commands.RegularCommand;
import lab6.client.interfaces.ClientCommandReceiver;

public class Info extends RegularCommand {
    public Info() {
        super("info", " - вывести информацию о коллекции");
    }

    @Override
    public void execute(ClientCommandReceiver serverReceiver, String[] args) {
        serverReceiver.info();
    }

    @Override
    public void clientExecute(String[] args, ClientCommandReceiver clientReceiver) {
        //empty
    }
}
