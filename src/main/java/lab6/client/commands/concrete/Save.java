package lab6.client.commands.concrete;

import lab6.client.commands.NonValidatingRegularCommand;
import lab6.client.interfaces.ClientCommandReceiver;

public class Save extends NonValidatingRegularCommand {
    public Save() {
        super("save", " - сохранить коллекцию в файл");
    }

    @Override
    public void execute(ClientCommandReceiver serverReceiver, String[] args) {
        serverReceiver.save();
    }
}
