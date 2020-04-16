package lab6.client;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import lab6.client.commands.Command;
import lab6.client.interfaces.Client;
import lab6.client.interfaces.ClientCommandReceiver;
import lab6.client.interfaces.CommandRepository;
import lab6.client.interfaces.ServerIO;

import java.io.IOException;
import java.util.Scanner;

import static lab6.util.BetterStrings.coloredRed;
import static lab6.util.BetterStrings.coloredYellow;

@Singleton
public class ClientApp implements Client {
    Scanner consoleScanner;
    CommandRepository commandRepository;
    ClientCommandReceiver clientCommandReceiver;
    ServerIO serverIO;

    @Inject
    public ClientApp(Scanner consoleScanner, CommandRepository commandRepository, ClientCommandReceiver clientCommandReceiver, ServerIO serverIO) {
        this.consoleScanner = consoleScanner;
        this.commandRepository = commandRepository;
        this.clientCommandReceiver = clientCommandReceiver;
        this.serverIO = serverIO;
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new ClientModule());
        Client clientApp = injector.getInstance(Client.class);
        clientApp.start();
    }

    @Override
    public void start() {
        System.out.println(coloredYellow(" ~ Начало работы клиента ~ "));
        serverIO.open();

        while (true) {
            System.out.print(">> ");

            String[] input = consoleScanner.nextLine().trim().split(" ");
            Command command = Parser.parseThenRun(input, commandRepository);

            if (command == null) System.out.println("Такой команды не существует. Список комманд: help.");
            else if (command.isServerSide()) {
                try {
                    if (!serverIO.isOpen()) {
                        serverIO.open();
                    }
                    serverIO.sendToServer(command);
                } catch (IOException e) {
                    System.out.println(coloredRed("Не получилось отправить команду: " + e.getMessage()));
                }

                try {
                    String result = serverIO.receiveFromServer();
                    System.out.println("Получен результат команды:\n" + result);
                } catch (IOException e) {
                    System.out.println(coloredRed("При получении ответа возникла ошибка: " + e.getMessage()));
                }
            }
        }
    }
}
