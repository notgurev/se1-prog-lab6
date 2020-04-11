package lab6.client;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lab6.client.commands.AbstractCommand;
import lab6.client.commands.Command;
import lab6.client.commands.concrete.*;
import lab6.client.interfaces.ClientCommandReceiver;
import lab6.client.interfaces.CommandRepository;
import lab6.client.interfaces.ServerIO;

import java.util.HashMap;

import static lab6.util.BetterStrings.multiline;

@Singleton
public class CommandInvoker implements CommandRepository {
    private final HashMap<String, AbstractCommand> commandMap = new HashMap<>(); // HashMap команд
    private final ClientCommandReceiver clientCommandReceiver;
    private final ServerIO serverIO;

    @Inject
    public CommandInvoker(ClientCommandReceiver clientCommandReceiver, ServerIO serverIO) {
        this.clientCommandReceiver = clientCommandReceiver;
        this.serverIO = serverIO;
        addCommand(
                new Add(),
                new Clear(),
                new CountLessThanDescription(),
                new ExecuteScript(this),
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
        clientCommandReceiver.setHelpText(multiline(commandMap.values().stream()
                .map(command -> command.getKey() + command.getHelpText()).toArray()));
    }

    private void addCommand(AbstractCommand... commands) {
        for (AbstractCommand command : commands) {
            commandMap.put(command.getKey(), command);
        }
    }


    @Override
    public ServerIO getServerIO() {
        return serverIO;
    }

    @Override
    public Command runCommand(String commandKey, String[] args) {
        if (!commandMap.containsKey(commandKey)) {
            System.out.println("Такой команды не существует. Список комманд: help.");
            return null;
        }
        Command command = commandMap.get(commandKey);
        // Выполняем клиентскую часть
        command.clientExecute(args, clientCommandReceiver);
        // Записываем в историю команд
        clientCommandReceiver.getCommandHistory().add(commandKey);
        // Возвращаем, чтобы отправить на сервер
        return command;
    }
}
