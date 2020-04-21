package lab6.client.commands;

import lab6.client.interfaces.ClientCommandReceiver;

public abstract class NonValidatingRegularCommand extends RegularCommand {
    public NonValidatingRegularCommand(String key, String helpText) {
        super(key, helpText);
    }

    @Override
    public final boolean clientExecute(String[] args, ClientCommandReceiver clientReceiver) {
        return true;
    }
}
