package lab6.client.commands.concrete;

import lab6.client.commands.RegularCommand;
import lab6.client.interfaces.ClientCommandReceiver;

public class PrintUniqueTunedInWorks extends RegularCommand {
    public PrintUniqueTunedInWorks() {
        super("print_unique_tuned_in_works", " - вывести уникальные значения поля tunedInWorks");
    }

    @Override
    public void execute(ClientCommandReceiver serverReceiver, String[] args) {
        serverReceiver.printUniqueTunedInWorks();
    }

    @Override
    public void clientExecute(String[] args, ClientCommandReceiver clientReceiver) {
        // empty
    }
}
