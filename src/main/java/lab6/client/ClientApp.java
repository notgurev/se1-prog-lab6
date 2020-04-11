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

import java.util.Scanner;

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
        System.out.println(" ~ Начало работы клиента ~ ");
        while (true) {
            System.out.print(">> ");
            // Считывание
            String[] input = consoleScanner.nextLine().trim().split(" ");
            // Парсинг и выполнение
            Command command = Parser.parseThenRun(input, commandRepository);
            if (command.isServerSide()) {
                serverIO.sendToServer(command); // TODO
            }
            // Тут видимо принимаем ответ
            // и по новой
        }
    }
}
