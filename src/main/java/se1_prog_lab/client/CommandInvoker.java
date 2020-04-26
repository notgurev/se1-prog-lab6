package se1_prog_lab.client;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import se1_prog_lab.client.commands.AbstractCommand;
import se1_prog_lab.client.commands.Command;
import se1_prog_lab.client.interfaces.ClientCommandReceiver;
import se1_prog_lab.client.interfaces.CommandRepository;
import se1_prog_lab.client.interfaces.ServerIO;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import static se1_prog_lab.util.BetterStrings.multiline;

@Singleton
public class CommandInvoker implements CommandRepository {
    private final HashMap<String, AbstractCommand> commandMap = new HashMap<>(); // HashMap команд
    private final ClientCommandReceiver clientCommandReceiver;
    private final ServerIO serverIO;

    @Inject
    public CommandInvoker(ClientCommandReceiver clientCommandReceiver, ServerIO serverIO, Set<AbstractCommand> commands) {
        this.clientCommandReceiver = clientCommandReceiver;
        this.serverIO = serverIO;
        addCommand(commands.toArray(new AbstractCommand[0]));
        clientCommandReceiver.setHelpText(multiline(commandMap.values().stream()
                .map(command -> command.getKey() + command.getHelpText()).toArray()));
    }

    private void addCommand(AbstractCommand... commands) {
        Arrays.stream(commands).forEach(command -> commandMap.put(command.getKey(), command));
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
        if (!command.clientExecute(args, clientCommandReceiver)) command = null;
        clientCommandReceiver.addToHistory(commandKey); // Записываем в историю команд
        return command; // Возвращаем, чтобы отправить на сервер
    }

    @Override
    public Command parseThenRun(String[] input) {
        String commandKey = input[0]; // Первый аргумент - ключ команды
        String[] ar = Arrays.copyOfRange(input, 1, input.length); // Создаем массив аргументов из старого (кроме 1 аргумента)
        // Передача ключа и аргументов обработчику команд
        return runCommand(commandKey, ar);
    }
}
