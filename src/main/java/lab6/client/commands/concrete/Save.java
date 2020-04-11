package lab6.client.commands.concrete;

import lab6.client.commands.RegularCommand;
import lab6.client.interfaces.ClientCommandReceiver;

public class Save extends RegularCommand {
    public Save() {
        super("save", " - сохранить коллекцию в файл");
    }

    @Override
    public void execute(ClientCommandReceiver serverReceiver, String[] args) {
        serverReceiver.save();
    }

    @Override
    public void clientExecute(String[] args, ClientCommandReceiver clientReceiver) {
        //empty
    }
}
