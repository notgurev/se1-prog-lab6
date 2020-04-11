package lab6.client.commands;

import lab6.client.interfaces.CommandReceiver;

public interface Executable {
    /**
     * Метод, выполняемый на сервере
     *
     * @param commandReceiver ресивер команд, который передается сервером как аргумент
     * @param args аргументы команды
     */
    void execute(CommandReceiver commandReceiver, String[] args);

    /**
     * Метод, выполняемый на клиенте
     *
     * @param args аргументы команды
     * @return true, если команда должна быть отправлена на сервер; false, если не должна.
     */
    boolean clientExecute(String[] args);
}
