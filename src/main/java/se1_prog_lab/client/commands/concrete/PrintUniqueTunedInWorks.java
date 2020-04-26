package se1_prog_lab.client.commands.concrete;

import se1_prog_lab.client.commands.NonValidatingRegularCommand;
import se1_prog_lab.server.interfaces.ServerCommandReceiver;

public class PrintUniqueTunedInWorks extends NonValidatingRegularCommand {
    public PrintUniqueTunedInWorks() {
        super("print_unique_tuned_in_works", " - вывести уникальные значения поля tunedInWorks");
    }

    @Override
    public void serverExecute(ServerCommandReceiver serverReceiver) {
        serverReceiver.printUniqueTunedInWorks();
    }
}
