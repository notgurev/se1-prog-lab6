package se1_prog_lab.client.commands;

import se1_prog_lab.client.interfaces.ClientCommandReceiver;
import se1_prog_lab.server.interfaces.ServerCommandReceiver;

import java.io.Serializable;

public interface Command extends Serializable {
    /**
     * Метод, выполняемый на сервере
     *
     * @param serverReceiver ресивер команд, который передается сервером как аргумент
     */
    void serverExecute(ServerCommandReceiver serverReceiver);

    /**
     * Метод, выполняемый на клиенте
     *  @param args           аргументы команды
     * @param clientReceiver серверный ресивер команд
     * @return true, если выполнилась нормально; false, если нет
     */
    boolean clientExecute(String[] args, ClientCommandReceiver clientReceiver);

    /**
     * @return true, если команду нужно отправить на сервер; false, если не нужно.
     */
    boolean isServerSide();

    String getHelpText();

    String getKey();
}
