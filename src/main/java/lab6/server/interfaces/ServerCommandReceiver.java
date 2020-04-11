package lab6.server.interfaces;

public interface ServerCommandReceiver {
    void setCollectionFile(String fileName);

    void add(Object o);

    void clear();

    void countLessThanDescription(String description);

    void info();

    void printUniqueTunedInWorks();

    void save();

    void show();

    void sort();

    void filterGreaterThanMinimalPoint(int minimalPoint);

    void removeByID(long id);

    void insertAt(Object carriedObject, int index);

    void update(Object carriedObject, long id);
}
