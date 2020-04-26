package se1_prog_lab.client.commands.concrete;

import se1_prog_lab.client.commands.ConstructingCommand;
import se1_prog_lab.client.interfaces.ClientCommandReceiver;
import se1_prog_lab.server.interfaces.ServerCommandReceiver;
import se1_prog_lab.util.ElementCreator;

public class Update extends ConstructingCommand {
    long id;

    public Update() {
        super("update", " id - обновить значение элемента коллекции, id которого равен заданному");
    }

    @Override
    public void serverExecute(ServerCommandReceiver serverReceiver) {
        serverReceiver.update(carriedObject, id);
    }

    @Override
    public boolean clientExecute(String[] args, ClientCommandReceiver clientReceiver) {
        boolean scriptMode = !clientReceiver.getExecutingScripts().isEmpty();
        try {
            id = Long.parseLong(args[0]);
            // Проверяем на отрицательный ID. Если это не скрипт, то предупреждаем пользователя.
            if (id < 0) {
                if (!scriptMode) System.out.println("Ошибка, id должен быть больше нуля!");
                else throw new NumberFormatException();
            }
            carriedObject = ElementCreator.buildLabWork(clientReceiver);
            return true;
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            if (!scriptMode) System.out.println("Введите корректный целочисленный id в качестве аргумента!");
            else throw new RuntimeException();
            return false;
        }
    }
}
