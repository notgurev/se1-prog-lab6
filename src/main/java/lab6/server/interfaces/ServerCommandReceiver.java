package lab6.server.interfaces;

import lab6.collection.LabWork;

public interface ServerCommandReceiver {
    void setCollectionFile(String fileName);

    void add(LabWork labWork);

    void clear();

    void countLessThanDescription(String description);

    void info();

    void printUniqueTunedInWorks();

    void save();

    void show();

    void sort();

    void filterGreaterThanMinimalPoint(int minimalPoint);

    void removeByID(long id);

    void insertAt(LabWork labWork, int index);

    void update(LabWork labWork, long id);
}
