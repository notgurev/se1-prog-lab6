package se1_prog_lab.client;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import se1_prog_lab.client.commands.Command;
import se1_prog_lab.client.interfaces.Client;
import se1_prog_lab.client.interfaces.CommandRepository;
import se1_prog_lab.client.interfaces.ServerIO;

import java.util.Scanner;

import static se1_prog_lab.util.BetterStrings.coloredYellow;

@Singleton
public class ClientApp implements Client {
    private final Scanner consoleScanner;
    private final CommandRepository commandRepository;
    private final ServerIO serverIO;

    @Inject
    public ClientApp(Scanner consoleScanner, CommandRepository commandRepository, ServerIO serverIO) {
        this.consoleScanner = consoleScanner;
        this.commandRepository = commandRepository;
        this.serverIO = serverIO;
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new ClientModule());
        Client clientApp = injector.getInstance(Client.class);
        clientApp.start();
    }

    @Override
    public void start() {
        System.out.println(coloredYellow("Начало работы клиента"));
        serverIO.open();

        while (true) {
            System.out.print(">> ");

            String[] input = consoleScanner.nextLine().trim().split(" ");
            Command command = commandRepository.parseThenRun(input);

            if (command != null) serverIO.sendAndReceive(command);
        }
    }


}
