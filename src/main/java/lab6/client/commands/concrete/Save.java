package lab6.client.commands.concrete;

import lab6.client.commands.NonValidatingRegularCommand;
import lab6.server.interfaces.ServerCommandReceiver;

public class Save extends NonValidatingRegularCommand {
    public Save() {
        super("save", " - сохранить коллекцию в файл");
    }

    @Override
    public void serverExecute(ServerCommandReceiver serverReceiver) {
        serverReceiver.save();
    }
}
