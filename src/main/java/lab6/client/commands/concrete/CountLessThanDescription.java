package lab6.client.commands.concrete;

import lab6.client.commands.RegularCommand;
import lab6.client.interfaces.ClientCommandReceiver;

public class CountLessThanDescription extends RegularCommand {
    String description;

    public CountLessThanDescription() {
        super("count_less_than_description", " description - вывести количество элементов, значение поля description которых меньше заданного");
    }

    @Override
    public void execute(ClientCommandReceiver serverReceiver, String[] args) {
        serverReceiver.countLessThanDescription(description);
    }

    @Override
    public void clientExecute(String[] args, ClientCommandReceiver clientReceiver) {
        description = String.join(" ", args);
    }
}
