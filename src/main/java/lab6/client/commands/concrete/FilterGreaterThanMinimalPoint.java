package lab6.client.commands.concrete;

import lab6.client.commands.RegularCommand;
import lab6.client.interfaces.ClientCommandReceiver;
import lab6.server.interfaces.ServerCommandReceiver;

public class FilterGreaterThanMinimalPoint extends RegularCommand {
    int minimalPoint;

    public FilterGreaterThanMinimalPoint() {
        super("filter_greater_than_minimal_point", " minimalPoint - вывести элементы, значение поля minimalPoint которых больше заданного");
    }

    @Override
    public void serverExecute(ServerCommandReceiver serverReceiver) {
        serverReceiver.filterGreaterThanMinimalPoint(minimalPoint);
    }

    @Override
    public void clientExecute(String[] args, ClientCommandReceiver clientReceiver) {
        try {
            minimalPoint = Integer.parseInt(args[0]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Пожалуйста, введите корректное число в качестве аргумента.");
        }
        /*
        TODO возможно клиент отправит команду на сервер, даже если нет ошибки
        Так что надо мб возвращать boolean => false, если что-то пошло не так.
         */
    }
}
