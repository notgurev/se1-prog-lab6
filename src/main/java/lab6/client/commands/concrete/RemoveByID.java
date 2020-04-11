package lab6.client.commands.concrete;

import lab6.client.commands.RegularCommand;
import lab6.client.interfaces.ClientCommandReceiver;
import lab6.server.interfaces.ServerCommandReceiver;

public class RemoveByID extends RegularCommand {
    long id;

    public RemoveByID() {
        super("remove_by_id", " id - удалить элемент из коллекции по его id");
    }

    @Override
    public void serverExecute(ServerCommandReceiver serverReceiver) {
        serverReceiver.removeByID(id);
    }

    @Override
    public void clientExecute(String[] args, ClientCommandReceiver clientReceiver) {
        try {
            id = Long.parseLong(args[0]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Пожалуйста, введите корректный числовой id в качестве аргумента.");
        }
    }
}
