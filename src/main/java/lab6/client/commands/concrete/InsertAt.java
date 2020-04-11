package lab6.client.commands.concrete;

import lab6.client.commands.ConstructingCommand;
import lab6.client.interfaces.ClientCommandReceiver;
import lab6.server.interfaces.ServerCommandReceiver;
import lab6.util.ElementCreator;

public class InsertAt extends ConstructingCommand {
    int index;

    public InsertAt() {
        super("insert_at", " index - добавить новый элемент в заданную позицию");
    }

    @Override
    public void serverExecute(ServerCommandReceiver serverReceiver, String[] args) {
        serverReceiver.insertAt(carriedObject, index);
    }

    @Override
    public void clientExecute(String[] args, ClientCommandReceiver clientReceiver) {
        try {
            index = Integer.parseInt(args[0]);
            carriedObject = ElementCreator.buildLabWork(clientReceiver);
        } catch (NumberFormatException e) {
            // Если не скрипт, то обрабатываем ошибку и выводим в консоль
            // Если скрипт, то передаем ошибку выше
            // TODO boolean false при ошибке
            if (clientReceiver.getExecutingScripts().isEmpty())
                System.out.println("Введите корректный положительный целочисленный индекс!");
            else throw new NumberFormatException();
        }
    }
}
