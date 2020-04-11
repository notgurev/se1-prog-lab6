package lab6.client.commands;

import lab6.server.interfaces.ServerCommandReceiver;

/**
 * Скриптовая клиентская команда.
 * В clientExecute() должна выполнить необходимые действия.
 * Не отправляется на сервер.
 */
public abstract class ScriptCommand extends AbstractCommand {
    public ScriptCommand(String key, String helpText) {
        super(true, false, helpText, key);
    }

    @Override
    public final void serverExecute(ServerCommandReceiver serverReceiver) {
        // empty and final
    }
}
