package lab6.client.commands.concrete;

import lab6.client.commands.NonValidatingRegularCommand;
import lab6.server.interfaces.ServerCommandReceiver;

public class Show extends NonValidatingRegularCommand {
    public Show() {
        super("show", " - вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
    }

    @Override
    public void serverExecute(ServerCommandReceiver serverReceiver, String[] args) {
        serverReceiver.show();
    }
}
