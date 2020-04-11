package lab6.client.commands;

/**
 * Класс клиент-серверной команды, которая в clientExecute() должна собрать объект, присвоить его полю carriedObject
 * и вернуть true, чтобы быть отправленной на сервер.
 */
public abstract class ConstructingCommand extends AbstractCommand {
    private Object carriedObject;

    public ConstructingCommand(boolean isScriptCalling, String helpText, String key) {
        super(isScriptCalling, true, helpText, key);
    }
}
