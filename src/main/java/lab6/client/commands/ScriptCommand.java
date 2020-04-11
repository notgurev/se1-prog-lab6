package lab6.client.commands;

/**
 * Скриптовая клиентская команда.
 * В clientExecute() должна выполнить необходимые действия.
 * Не отправляется на сервер.
 */
public abstract class ScriptCommand extends AbstractCommand {
    public ScriptCommand(String helpText, String key) {
        super(true, false, helpText, key);
    }
}
