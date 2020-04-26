package se1_prog_lab.client.commands;

import se1_prog_lab.server.interfaces.ServerCommandReceiver;

/**
 * Клиентская команда.
 * Не создает объект.
 * В clientExecute() должна выполнить необходимые действия.
 * Не отправляется на сервер.
 */
public abstract class ClientSideCommand extends AbstractCommand {
    public ClientSideCommand(String key, String helpText) {
        super(false, helpText, key);
    }

    @Override
    public final void serverExecute(ServerCommandReceiver clientCommandReceiver) {
        // final and empty
    }
}
