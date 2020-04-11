package lab6.client.commands.concrete;

import lab6.client.Parser;
import lab6.client.commands.ScriptCommand;
import lab6.client.interfaces.ClientCommandReceiver;
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
    public ExecuteScript() {
        super("execute_script", " file_name - считать и исполнить скрипт из указанного файла");
    }

    @Override
    public void clientExecute(String[] args, ClientCommandReceiver clientReceiver) {
        ArrayList<Integer> linesWithErrors = new ArrayList<>();
        try (FileReader fileReader = new FileReader(args[0]); Scanner scanner = new Scanner(fileReader)) {
            clientReceiver.setScriptScanner(scanner);
            String[] line;
            int currentLine = 0;
            while (scanner.hasNextLine()) {
                currentLine++;
                line = scanner.nextLine().split(" ");
                try {
                    Parser.parseThenRun(line, clientReceiver.getCommandRepository()); // TODO опять нужен repository, а есть только ресивер
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
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Пожалуйста, введите путь к файлу скрипта.");
        }
    }
}
