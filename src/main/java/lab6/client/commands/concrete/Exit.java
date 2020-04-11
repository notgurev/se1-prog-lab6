package lab6.client.commands.concrete;

import lab6.client.commands.ClientSideCommand;
import lab6.client.interfaces.ClientCommandReceiver;

public class Exit extends ClientSideCommand {
    public Exit() {
        super("exit", " - завершить программу без сохранения в файл");
    }

    @Override
    public void clientExecute(String[] args, ClientCommandReceiver clientReceiver) {
        clientReceiver.exit();
    }
}
