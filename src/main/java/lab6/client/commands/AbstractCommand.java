package lab6.client.commands;

/**
 * Базовый класс команды
 */
public abstract class AbstractCommand implements Command {
    private final boolean isScriptCalling;
    private final boolean serverSide;
    private final String helpText;
    private final String key;

    public AbstractCommand(boolean isScriptCalling, boolean serverSide, String helpText, String key) {
        this.isScriptCalling = isScriptCalling;
        this.serverSide = serverSide;
        this.helpText = helpText;
        this.key = key;
    }

    @Override
    public String getHelpText() {
        return null;
    }

    @Override
    public String getKey() {
        return null;
    }

    @Override
    public boolean isServerSide() {
        return serverSide;
    }
}
