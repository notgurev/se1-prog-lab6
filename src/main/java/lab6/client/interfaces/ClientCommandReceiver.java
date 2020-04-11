package lab6.client.interfaces;

import java.util.Collection;
import java.util.Scanner;

public interface ClientCommandReceiver {
    Scanner getConsoleScanner();

    Scanner getScriptScanner();

    Collection<Object> getExecutingScripts();
}
