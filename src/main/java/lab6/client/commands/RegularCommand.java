package lab6.client.commands;

/**
 * Обычная клиент-серверная команда.
 * Не создает объект.
 * В clientExecute() должна выполнить валидацию, если это необходимо.
 * В execute() выполняет необходимые действия.
 * Отправляется на сервер.
 */
public abstract class RegularCommand extends AbstractCommand {
    public RegularCommand(String helpText, String key) {
        super(false, true, helpText, key);
    }
}
