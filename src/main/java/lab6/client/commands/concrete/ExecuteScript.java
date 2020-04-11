package lab6.client.commands.concrete;

import lab6.client.Parser;
import lab6.client.commands.Command;
import lab6.client.commands.ScriptCommand;
import lab6.client.interfaces.ClientCommandReceiver;
import lab6.client.interfaces.CommandRepository;
import lab6.client.interfaces.ServerIO;
import lab6.collection.LabWork;
import lab6.exceptions.LabWorkFieldException;
import lab6.exceptions.SelfCallingScriptException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static lab6.util.BetterStrings.coloredRed;
import static lab6.util.BetterStrings.coloredYellow;

public class ExecuteScript extends ScriptCommand {
    private CommandRepository commandRepository;
    private ServerIO serverIO;

    public ExecuteScript(CommandRepository commandRepository) {
        super("execute_script", " file_name - считать и исполнить скрипт из указанного файла");
        this.commandRepository = commandRepository;
    }

    @Override
    public void clientExecute(String[] args, ClientCommandReceiver clientReceiver) {
        // Проверка на пустой аргумент
        if (args.length == 0) {
            System.out.println("Пожалуйста, введите путь к файлу скрипта.");
            return;
        }
        String scriptFileName = args[0];

        // Проверка на рекурсивный скрипт
        if (clientReceiver.getExecutingScripts().contains(scriptFileName)) {
            throw new SelfCallingScriptException();
        }

        try (FileReader fileReader = new FileReader(scriptFileName); Scanner scanner = new Scanner(fileReader)) {
            // Добавляем имя скрипта в массив выполняющихся скриптов
            clientReceiver.getExecutingScripts().add(scriptFileName);

            ArrayList<Integer> linesWithErrors = new ArrayList<>();
            clientReceiver.setScriptScanner(scanner);
            String[] line;
            int currentLine = 0;
            while (scanner.hasNextLine()) {
                currentLine++;
                line = scanner.nextLine().split(" ");
                try {
                    Command command = Parser.parseThenRun(line, commandRepository);
                    serverIO.sendToServer(command);
                    currentLine += LabWork.getNumberOfFields();
                } catch (SelfCallingScriptException e) {
                    linesWithErrors.add(currentLine);
                    System.out.println(coloredRed("В скрипте (строка "
                            + currentLine + ") присутствует рекурсивный вызов. Он не будет выполняться."));
                } catch (NoSuchElementException | NumberFormatException | LabWorkFieldException e) {
                    System.out.println(coloredRed(
                            "Где-то в скрипте была попытка создать элемент коллекции, но что-то пошло не так."));
                    linesWithErrors.add(currentLine);
                } catch (Exception e) {
                    e.printStackTrace();
                    linesWithErrors.add(currentLine);
                }
            }
            if (!linesWithErrors.isEmpty()) System.out.println(coloredRed(
                    "Следующие строки не содержат существующих команд или не могли быть выполнены: " + linesWithErrors.toString()));
            System.out.println(coloredYellow("Выполенение скрипта " + Arrays.toString(args) + " завершено."));
        } catch (FileNotFoundException e) {
            System.out.println("Файл скрипта не найден!");
        } catch (IOException e) {
            System.out.println("Что-то пошло не так при чтении файла скрипта!");
        } finally {
            // Когда закончили, удаляем название скрипта из executingScripts
            clientReceiver.getExecutingScripts().remove(scriptFileName);
        }
    }
}
