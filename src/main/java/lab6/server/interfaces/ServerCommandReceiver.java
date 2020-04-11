package lab6.server.interfaces;

import java.util.Scanner;

public interface ServerCommandReceiver {
    void setCollectionFile(String fileName);

    Scanner getConsoleScanner();

    Scanner getScriptScanner();

    void add();

    void clear();

    void countLessThanDescription(String description);

    void info();

    void printUniqueTunedInWorks();

    void save();

    void show();

    void sort();
}
