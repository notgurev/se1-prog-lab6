package lab6.client.commands;

/**
 * Класс клиент-серверной команды, которая в clientExecute() должна собрать объект, присвоить его полю carriedObject
 * и вернуть true, чтобы быть отправленной на сервер.
 */
public abstract class ConstructingCommand extends Command {
    Object carriedObject;

    public ConstructingCommand(String helpText, String key) {
        super(helpText, key);
    }

    @Override
    public boolean clientExecute(String[] args) {
        Object constructedObject = new Object();
        return true;
    }
}
