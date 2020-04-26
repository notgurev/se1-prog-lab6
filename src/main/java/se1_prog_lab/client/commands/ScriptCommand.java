package se1_prog_lab.client.commands;

import se1_prog_lab.server.interfaces.ServerCommandReceiver;

/**
 * Скриптовая клиентская команда.
 * В clientExecute() должна выполнить необходимые действия.
 * Не отправляется на сервер.
 */
public abstract class ScriptCommand extends AbstractCommand {
    public ScriptCommand(String key, String helpText) {
        super(false, helpText, key);
    }

    @Override
    public final void serverExecute(ServerCommandReceiver serverReceiver) {
        // empty and final
    }
}
