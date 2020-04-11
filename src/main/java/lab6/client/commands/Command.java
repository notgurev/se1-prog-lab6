package lab6.client.commands;

import lab6.client.interfaces.CommandReceiver;

/**
 * Базовый класс команды
 */
public abstract class Command implements Executable {
    protected boolean isScriptCalling = false;
    private String helpText;
    private String key;

    public Command(String helpText, String key) {
        this.helpText = helpText;
        this.key = key;
    }

    public String getHelpText() {
        return helpText;
    }

    public String getKey() {
        return key;
    }
}
