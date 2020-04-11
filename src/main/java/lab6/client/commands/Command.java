package lab6.client.commands;

import lab6.client.interfaces.CommandReceiver;

public interface Command {
    /**
     * Метод, выполняемый на сервере
     *
     * @param serverReceiver ресивер команд, который передается сервером как аргумент
     * @param args           аргументы команды
     */
    void execute(CommandReceiver serverReceiver, String[] args);

    /**
     * Метод, выполняемый на клиенте
     *
     * @param args           аргументы команды
     * @param clientReceiver серверный ресивер команд
     */
    void clientExecute(String[] args, CommandReceiver clientReceiver);

    /**
     * @return true, если команду нужно отправить на сервер; false, если не нужно.
     */
    boolean isServerSide();

    String getHelpText();

    String getKey();
}
