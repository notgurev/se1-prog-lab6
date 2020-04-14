package lab6.client.interfaces;

import lab6.util.LimitedLinkedList;

import java.util.Collection;
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

    LimitedLinkedList<String> getCommandHistory();
}
