package lab6.client.commands.concrete;

import lab6.client.commands.ConstructingCommand;
import lab6.client.interfaces.ClientCommandReceiver;
import lab6.server.interfaces.ServerCommandReceiver;
import lab6.util.ElementCreator;

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
    public void clientExecute(String[] args, ClientCommandReceiver clientReceiver) {
        boolean scriptMode = clientReceiver.getExecutingScripts().isEmpty();
        try {
            id = Long.parseLong(args[0]);
            // Проверяем на отрицательный ID. Если это не скрипт, то предупреждаем пользователя.
            if (id < 0) {
                if (scriptMode) System.out.println("Ошибка, id должен быть больше нуля!");
                else throw new NumberFormatException();
            }
            carriedObject = ElementCreator.buildLabWork(clientReceiver);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            if (scriptMode) System.out.println("Введите корректный целочисленный id в качестве аргумента!");
            else throw new RuntimeException();
        }
    }
}
