package lab6.client.commands;

import lab6.client.interfaces.CommandReceiver;

/**
 * Класс клиентской команды, которая в clientExecute() должна выполнить необходимые действия
 * и вернуть false, чтобы НЕ быть отправленной на сервер.
 */
public abstract class ClientSideCommand extends AbstractCommand {
    public ClientSideCommand(boolean isScriptCalling, String helpText, String key) {
        super(isScriptCalling, false, helpText, key);
    }

    @Override
    public final void execute(CommandReceiver commandReceiver, String[] args) {
        // Здесь ничего нет, так как на сервер команда не отправляется
        // Также метод final, чтобы его нельзя было переопределить
    }
}
