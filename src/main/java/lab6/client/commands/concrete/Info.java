package lab6.client.commands.concrete;

import lab6.client.commands.NonValidatingRegularCommand;
import lab6.client.interfaces.ClientCommandReceiver;

public class Info extends NonValidatingRegularCommand {
    public Info() {
        super("info", " - вывести информацию о коллекции");
    }

    @Override
    public void execute(ClientCommandReceiver serverReceiver, String[] args) {
        serverReceiver.info();
    }
}
