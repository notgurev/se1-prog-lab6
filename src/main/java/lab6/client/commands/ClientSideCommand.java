package lab6.client.commands;

import lab6.client.interfaces.CommandReceiver;

/**
 * Класс клиентской команды, которая в clientExecute() должна выполнить необходимые действия
 * и вернуть false, чтобы НЕ быть отправленной на сервер.
 */
public abstract class ClientSideCommand extends Command {
    public ClientSideCommand(String helpText, String key) {
        super(helpText, key);
    }

    @Override
    public void execute(CommandReceiver commandReceiver, String[] args) {
        // Здесь ничего нет, так как на сервер команда не отправляется
        // Так что execute прописывать не обязательно
    }

    @Override
    public boolean clientExecute(String[] args) {
        return false;
    }
}
