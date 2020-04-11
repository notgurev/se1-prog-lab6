package lab6.client.commands;

import lab6.server.interfaces.ServerCommandReceiver;

/**
 * Клиентская команда.
 * Не создает объект.
 * В clientExecute() должна выполнить необходимые действия.
 * Не отправляется на сервер.
 */
public abstract class ClientSideCommand extends AbstractCommand {
    public ClientSideCommand(String key, String helpText) {
        super(false, false, helpText, key);
    }

    @Override
    public final void serverExecute(ServerCommandReceiver clientCommandReceiver, String[] args) {
        // Здесь ничего нет, так как на сервер команда не отправляется
        // Также метод final, чтобы его нельзя было переопределить
    }
}
