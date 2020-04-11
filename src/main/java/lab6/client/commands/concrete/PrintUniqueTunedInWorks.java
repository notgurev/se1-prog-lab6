package lab6.client.commands.concrete;

import lab6.client.commands.NonValidatingRegularCommand;
import lab6.client.interfaces.ClientCommandReceiver;

public class PrintUniqueTunedInWorks extends NonValidatingRegularCommand {
    public PrintUniqueTunedInWorks() {
        super("print_unique_tuned_in_works", " - вывести уникальные значения поля tunedInWorks");
    }

    @Override
    public void execute(ClientCommandReceiver serverReceiver, String[] args) {
        serverReceiver.printUniqueTunedInWorks();
    }
}
