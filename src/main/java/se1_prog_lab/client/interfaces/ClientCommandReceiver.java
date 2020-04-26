package se1_prog_lab.client.interfaces;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Scanner;

public interface ClientCommandReceiver {
    Scanner getConsoleScanner();

    Scanner getScriptScanner();

    Collection<String> getExecutingScripts();

    void help();

    void history();

    void exit();

    void setScriptScanner(Scanner scanner);

    void setHelpText(String helpText);

    LinkedList<String> getCommandHistory();

    void addToHistory(String commandKey);
}
