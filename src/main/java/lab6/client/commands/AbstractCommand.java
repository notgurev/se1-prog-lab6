package lab6.client.commands;

/**
 * Базовый класс команды
 */
public abstract class AbstractCommand implements Command {
    private final boolean serverSide;
    private final String helpText;
    private final String key;

    public AbstractCommand(boolean serverSide, String helpText, String key) {
        this.serverSide = serverSide;
        this.helpText = helpText;
        this.key = key;
    }

    @Override
    public String getHelpText() {
        return helpText;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public boolean isServerSide() {
        return serverSide;
    }
}
