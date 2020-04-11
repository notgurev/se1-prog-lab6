package lab6.client;

import com.google.inject.Inject;
import lab6.client.commands.AbstractCommand;
import lab6.client.commands.Command;
import lab6.client.commands.concrete.*;
import lab6.client.interfaces.ClientCommandReceiver;
import lab6.client.interfaces.CommandRepository;

import java.util.HashMap;

import static lab6.util.BetterStrings.multiline;

public class CommandInvoker implements CommandRepository {
    private final HashMap<String, AbstractCommand> commandMap = new HashMap<>(); // HashMap команд
    private final ClientCommandReceiver clientCommandReceiver;

    @Inject
    public CommandInvoker(ClientCommandReceiver clientCommandReceiver) {
        this.clientCommandReceiver = clientCommandReceiver;
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
        clientCommandReceiver.setHelpText(multiline((String[]) commandMap.values().stream()
                .map(command -> command.getKey() + command.getHelpText()).toArray()));
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
        Command command = commandMap.get(commandKey);
        if (command instanceof ExecuteScript) {
            ExecuteScript scriptCommand = (ExecuteScript) command;
            scriptCommand.setCommandRepository(this);
        }
        command.clientExecute(args, clientCommandReceiver);
        clientCommandReceiver.getCommandHistory().add(commandKey);
    }
}
