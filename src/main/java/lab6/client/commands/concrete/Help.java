package lab6.client.commands.concrete;

import lab6.client.commands.ClientSideCommand;
import lab6.client.interfaces.ClientCommandReceiver;

public class Help extends ClientSideCommand {
    public Help() {
        super("help", " - вывести справку по доступным командам");
    }

    @Override
    public void clientExecute(String[] args, ClientCommandReceiver clientReceiver) {
        // TODO инвалидская хрень, так как по идее это должен делать CommandRepository
        clientReceiver.help();
    }
}
