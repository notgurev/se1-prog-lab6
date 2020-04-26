package se1_prog_lab.client.commands.concrete;

import se1_prog_lab.client.commands.RegularCommand;
import se1_prog_lab.client.interfaces.ClientCommandReceiver;
import se1_prog_lab.server.interfaces.ServerCommandReceiver;

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
    public boolean clientExecute(String[] args, ClientCommandReceiver clientReceiver) {
        try {
            minimalPoint = Integer.parseInt(args[0]);
            return true;
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            if (clientReceiver.getExecutingScripts().isEmpty())
                System.out.println("Пожалуйста, введите корректное число в качестве аргумента.");
            else throw new NumberFormatException();
            return false;
        }
    }
}
