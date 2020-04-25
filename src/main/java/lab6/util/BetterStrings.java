package lab6.util;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Класс со всякими удобствами для вывода в консоль
 */
public class BetterStrings {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    /**
     * Метод, создающий строку из массива строк с переносами строк
     *
     * @param s массив строк
     * @return строка с переносами строк
     */
    public static String multiline(Object... s) {
        if (s == null) return "";
        List<Object> input = Arrays.asList(s);
        return input.stream().filter(Objects::nonNull).map(Object::toString).collect(Collectors.joining("\n"));
    }

    /**
     * Метод, красящий строку в указанный цвет
     *
     * @param s     строка
     * @param color цвет
     * @return покрашенную строку
     */
    public static String coloredString(String s, String color) {
        return color + s + ANSI_RESET;
    }

    /**
     * Метод, красящий строку, если она null
     *
     * @param s строка
     * @return покрашенную строку
     */
    public static Object blueStringIfNull(Object s) {
        return s == null ? ANSI_BLUE + "пусто" + ANSI_RESET : s;
    }

    public static String coloredRed(String s) {
        return ANSI_RED + s + ANSI_RESET;
    }

    public static String coloredYellow(String s) {
        return ANSI_YELLOW + s + ANSI_RESET;
    }
}
