package lab6.server.interfaces;

import java.util.Scanner;

public interface ServerCommandReceiver {
    void setCollectionFile(String fileName);

    Scanner getConsoleScanner();

    Scanner getScriptScanner();
}
