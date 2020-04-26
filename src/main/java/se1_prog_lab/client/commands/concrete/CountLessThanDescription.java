package se1_prog_lab.client.commands.concrete;

import se1_prog_lab.client.commands.RegularCommand;
import se1_prog_lab.client.interfaces.ClientCommandReceiver;
import se1_prog_lab.server.interfaces.ServerCommandReceiver;

public class CountLessThanDescription extends RegularCommand {
    String description;

    public CountLessThanDescription() {
        super("count_less_than_description", " description - вывести количество элементов, значение поля description которых меньше заданного");
    }

    @Override
    public void serverExecute(ServerCommandReceiver serverReceiver) {
        serverReceiver.countLessThanDescription(description);
    }

    @Override
    public boolean clientExecute(String[] args, ClientCommandReceiver clientReceiver) {
        description = String.join(" ", args);
        return true;
    }
}
