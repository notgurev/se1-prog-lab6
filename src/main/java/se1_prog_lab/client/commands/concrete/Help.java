package se1_prog_lab.client.commands.concrete;

import se1_prog_lab.client.commands.ClientSideCommand;
import se1_prog_lab.client.interfaces.ClientCommandReceiver;

public class Help extends ClientSideCommand {
    public Help() {
        super("help", " - вывести справку по доступным командам");
    }

    @Override
    public boolean clientExecute(String[] args, ClientCommandReceiver clientReceiver) {
        clientReceiver.help();
        return true;
    }
}
