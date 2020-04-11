package lab6.client;

import lab6.client.commands.Command;
import lab6.client.interfaces.CommandRepository;

import java.util.Arrays;

public class Parser {
    public static Command parseThenRun(String[] input, CommandRepository commandRepository) {
        // Парсинг
        String commandKey = input[0]; // Первый аргумент - ключ команды
        String[] ar = Arrays.copyOfRange(input, 1, input.length); // Создаем массив аргументов из старого (кроме 1 аргумента)
        // Передача ключа и аргументов обработчику команд
        return commandRepository.runCommand(commandKey, ar);
    }
}
