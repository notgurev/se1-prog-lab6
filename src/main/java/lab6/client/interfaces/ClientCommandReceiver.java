package lab6.client.interfaces;

import java.util.Collection;
import java.util.Scanner;

public interface ClientCommandReceiver {
    Scanner getConsoleScanner();

    Scanner getScriptScanner();

    Collection<String> getExecutingScripts();

    void help();

    void history();

    void exit();

    CommandRepository getCommandRepository();

    void setScriptScanner(Scanner scanner);
}
