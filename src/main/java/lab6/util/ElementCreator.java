package lab6.util;

import lab6.client.interfaces.ClientCommandReceiver;
import lab6.collection.*;
import lab6.exceptions.LabWorkFieldException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

import static lab6.util.ValidatingReader.*;

/**
 * Класс, предоставляющий методы для создания экземпляров LabWork
 */
public class ElementCreator {
    /**
     * Метод для создания LW из консоли
     *
     * @param scanner Scanner, откуда считывается информация
     * @return созданный LabWork
     */
    public static LabWork fromConsole(Scanner scanner) {
        String name = readString(scanner, "Введите название: ", false, null);
        long coordinatesX = readLong(scanner, "Введите координату X: ", false, 625L, "MAX");
        Float coordinatesY = readFloat(scanner, "Введите координату Y: ", false, null, "NO_LIMIT");
        Integer minimalPoint = readInteger(scanner, "Введите minimalPoint (можно оставить пустым): ", true, 0L, "MIN");
        String description = readString(scanner, "Введите описание: ", false, null);
        Integer tunedInWorks = readInteger(scanner, "Введите tunedInWorks (можно оставить пустым): ", true, null, "NO_LIMIT");
        Difficulty difficulty = readEnum(scanner, Difficulty.class, "Введите сложность: ", false);
        return new LabWork(name, new Coordinates(coordinatesX, coordinatesY), minimalPoint, description, tunedInWorks, difficulty, createPerson(scanner));
    }

    private static Person createPerson(Scanner scanner) {
        String name = readString(scanner, "Введите имя автора: ", false, null);
        Float height = readFloat(scanner, "Введите рост автора (можно оставить пустым): ", true, 0L, "MIN");
        String passportID = readString(scanner, "Введите номер паспорта: ", false, 9);
        Color hairColor = readEnum(scanner, Color.class, "Введите цвет волос автора (можно оставить пустым): ", true);
        Integer locX = readInteger(scanner, "Введите местоположение по X: ", false, null, "NO_LIMIT");
        float locY = readFloat(scanner, "Введите местоположение по Y: ", false, null, "NO_LIMIT");
        Integer locZ = readInteger(scanner, "Введите местоположение по Z: ", false, null, "NO_LIMIT");
        return new Person(name, height, passportID, hairColor, new Location(locX, locY, locZ));
    }

    /**
     * Метод для создания LW из массива String[]
     *
     * @param args массив полей
     * @return созданный LabWork
     */
    public static LabWork fromStringArray(String[] args, boolean fromCSV) throws LabWorkFieldException, NumberFormatException, ArrayIndexOutOfBoundsException {
        Arrays.setAll(args, i -> args[i].trim());
        IntStream.range(0, args.length).filter(i -> args[i].equals("")).forEach(i -> args[i] = null);
        LabWork labWork = new LabWork();
        labWork.setName(args[0]);
        labWork.setCoordinates(Long.parseLong(args[1]), Float.parseFloat(args[2]));
        labWork.setMinimalPoint(args[3] == null ? null : Integer.valueOf(args[3]));
        labWork.setDescription(args[4]);
        labWork.setTunedInWorks(args[5] == null ? null : Integer.valueOf(args[5]));
        labWork.setDifficulty(Difficulty.valueOf(args[6]));
        labWork.getAuthor().setName(args[7]);
        labWork.getAuthor().setHeight(args[8] == null ? null : Float.parseFloat(args[8]));
        labWork.getAuthor().setPassportID(args[9]);
        labWork.getAuthor().setHairColor(nullableValueOf(Color.class, args[10]));
        labWork.getAuthor().setLocation(Integer.parseInt(args[11]), Float.parseFloat(args[12]), Integer.valueOf(args[13]));
        if (fromCSV) {
            labWork.preSetId(Long.valueOf(args[14]));
            labWork.setCreationDate(LocalDateTime.parse(args[15]));
        } else {
            labWork.setCreationDate(LocalDateTime.now());
        }
        return labWork;
    }

    /**
     * Метод, позволяющий получить из строки либо константу enum, либо null
     *
     * @param enumType тип Enum
     * @param s        строка
     * @param <T>      тип Enum
     * @return null или константу Enum
     */
    private static <T extends Enum<T>> T nullableValueOf(Class<T> enumType, String s) {
        if (s == null || !hasSuchConstant(enumType, s)) {
            return null;
        } else {
            return T.valueOf(enumType, s);
        }
    }

    /**
     * Метод для считывания и создания экземпляра LabWork из скрипта
     *
     * @param currentScanner сканнер, в данный момент использующийся для считывания скрипта из файла
     * @return экземпляр LabWork
     */
    public static LabWork fromScript(Scanner currentScanner) {
        ArrayList<String> dataForLabWorkCreator = new ArrayList<>();
        for (int i = 0; i < LabWork.getNumberOfFields(); i++) {
            dataForLabWorkCreator.add(currentScanner.nextLine());
        }
        String[] inter = new String[dataForLabWorkCreator.size()];
        inter = dataForLabWorkCreator.toArray(inter);
        return ElementCreator.fromStringArray(inter, false);
    }

    public static LabWork buildLabWork(ClientCommandReceiver clientCommandReceiver) {
        if (clientCommandReceiver.getExecutingScripts().isEmpty()) {
            // Создаем LabWork из консоли
            return fromConsole(clientCommandReceiver.getConsoleScanner());
        } else {
            // Создаем LabWork из скрипта
            return fromScript(clientCommandReceiver.getScriptScanner());
        }
    }
}
