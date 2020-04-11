package lab6.client;

import com.google.inject.Inject;
import lab6.client.commands.AbstractCommand;
import lab6.client.commands.concrete.*;
import lab6.client.interfaces.ClientCommandReceiver;
import lab6.client.interfaces.CommandRepository;
import lab6.exceptions.SelfCallingScriptException;
import lab6.server.interfaces.CollectionWrapper;
import lab6.util.LimitedStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class CommandInvoker implements CommandRepository {
    private ClientCommandReceiver clientCommandReceiver;
    private final HashMap<String, AbstractCommand> commandMap = new HashMap<>(); // HashMap команд
    private Scanner consoleScanner; // Сканнер для считывания команд из консоли TODO final?
    private Scanner scriptScanner; // Сканнер для считывания содержимого скрипта
    private CollectionWrapper targetCollection; // Коллекция, с которой работает CommandManager TODO final?
    private final LimitedStack<String> commandHistory = new LimitedStack<>(13); // История команд (клиент-сайд)
    private final HashSet<String> executingScripts = new HashSet<>(); // Выполняющиеся в данный момент скрипты

    @Inject
    public CommandInvoker(ClientCommandReceiver clientCommandReceiver, Scanner consoleScanner, Scanner scriptScanner, CollectionWrapper targetCollection) {
        this.clientCommandReceiver = clientCommandReceiver;
        this.consoleScanner = consoleScanner;
        this.scriptScanner = scriptScanner;
        this.targetCollection = targetCollection;

        addCommand(
                new Add(),
                new Clear(),
                new CountLessThanDescription(),
                new ExecuteScript(),
                new Exit(),
                new FilterGreaterThanMinimalPoint(),
                new Help(),
                new History(),
                new Info(),
                new InsertAt(),
                new PrintUniqueTunedInWorks(),
                new RemoveByID(),
                new Save(),
                new Show(),
                new Sort(),
                new Update()
        );
    }

    private void addCommand(AbstractCommand... commands) {
        for (AbstractCommand command : commands) {
            commandMap.put(command.getKey(), command);
        }
    }


    @Override
    public void runCommand(String commandKey, String[] args) {
        if (!commandMap.containsKey(commandKey)) {
            System.out.println("Такой команды не существует. Список комманд: help.");
            return;
        }
        commandMap.get(commandKey).clientExecute(args, clientCommandReceiver);
        commandHistory.add(commandKey);
    }
}
