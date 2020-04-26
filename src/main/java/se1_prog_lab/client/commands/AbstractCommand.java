package se1_prog_lab.client.commands;

/**
 * Базовый класс команды
 */
public abstract class AbstractCommand implements Command {
    private final transient boolean  serverSide;
    private final transient String helpText;
    private final transient String key;

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
