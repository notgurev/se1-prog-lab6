package lab6.client;

import com.google.inject.Inject;
import lab6.client.interfaces.ClientCommandReceiver;
import lab6.util.LimitedStack;

import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;

import static lab6.util.BetterStrings.coloredYellow;

public class ClientCommandReceiverImpl implements ClientCommandReceiver {
    private final Scanner consoleScanner; // Сканнер для считывания команд из консоли
    private final HashSet<String> executingScripts = new HashSet<>(); // Выполняющиеся в данный момент скрипты
    private final LimitedStack<String> commandHistory = new LimitedStack<>(13); // История команд (клиент-сайд)
    private Scanner scriptScanner; // Сканнер для считывания содержимого скрипта
    private String helpText;

    @Inject
    public ClientCommandReceiverImpl(Scanner consoleScanner) {
        this.consoleScanner = consoleScanner;
    }

    @Override
    public Scanner getConsoleScanner() {
        return consoleScanner;
    }

    @Override
    public Scanner getScriptScanner() {
        return scriptScanner;
    }

    @Override
    public void setScriptScanner(Scanner scriptScanner) {
        this.scriptScanner = scriptScanner;
    }

    @Override
    public void exit() {
        System.out.println(coloredYellow("Завершение работы."));
        System.exit(0);
    }

    @Override
    public Collection<String> getExecutingScripts() {
        return executingScripts;
    }

    @Override
    public void setHelpText(String helpText) {
        this.helpText = helpText;
    }

    @Override
    public LimitedStack<String> getCommandHistory() {
        return commandHistory;
    }

    @Override
    public void help() {
        System.out.println(helpText);
    }

    @Override
    public void history() {
        if (commandHistory.isEmpty()) {
            System.out.println(coloredYellow("История выполненных команд пуста!"));
        } else {
            System.out.println("Предыдущие команды (начиная с последней):");
            commandHistory.forEach(System.out::println);
        }
    }
}
