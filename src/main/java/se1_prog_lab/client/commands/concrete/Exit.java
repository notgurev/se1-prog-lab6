package se1_prog_lab.client.commands.concrete;

import se1_prog_lab.client.commands.ClientSideCommand;
import se1_prog_lab.client.interfaces.ClientCommandReceiver;

public class Exit extends ClientSideCommand {
    public Exit() {
        super("exit", " - завершить программу без сохранения в файл");
    }

    @Override
    public boolean clientExecute(String[] args, ClientCommandReceiver clientReceiver) {
        clientReceiver.exit();
        return true;
    }
}
