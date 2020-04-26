package se1_prog_lab.client;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import se1_prog_lab.client.interfaces.ClientCommandReceiver;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

import static se1_prog_lab.util.BetterStrings.coloredYellow;

@Singleton
public class ClientCommandReceiverImpl implements ClientCommandReceiver {
    private final Scanner consoleScanner; // Сканнер для считывания команд из консоли
    private final HashSet<String> executingScripts = new HashSet<>(); // Выполняющиеся в данный момент скрипты
    private final LinkedList<String> commandHistory = new LinkedList<>(); // История команд (клиент-сайд)
    private final int HISTORY_SIZE_LIMIT = 13;
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
    public LinkedList<String> getCommandHistory() {
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

    @Override
    public void addToHistory(String commandKey) {
        commandHistory.addFirst(commandKey);
        while (commandHistory.size() > HISTORY_SIZE_LIMIT) {
            commandHistory.removeLast();
        }
    }
}
