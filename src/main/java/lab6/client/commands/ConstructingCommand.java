package lab6.client.commands;

import lab6.collection.LabWork;

/**
 * Клиент-серверная команда, создающая объект.
 * Создает объект.
 * В clientExecute() должна создать объект.
 * В execute() выполняет необходимые действия.
 * Отправляется на сервер.
 */
public abstract class ConstructingCommand extends AbstractCommand {
    protected LabWork carriedObject;

    public ConstructingCommand(String key, String helpText) {
        super(false, true, helpText, key);
    }
}
