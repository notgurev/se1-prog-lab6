package lab6.server.interfaces;

import lab6.collection.LabWork;
import lab6.exceptions.NoElementWithSuchIdException;

import java.time.LocalDate;
import java.util.Set;

public interface CollectionWrapper {
    long add(LabWork labWork);

    boolean isEmpty();

    String[] toArray();

    void clear();

    long countLessThanDescription(String description);

    String getCollectionType();

    int getSize();

    LocalDate getInitDate();

    boolean sort();

    String showAll();

    Set<Integer> getUniqueTunedInWorks();

    String filterGreaterThanMinimalPoint(Integer minimalPoint);

    void removeByID(long id) throws NoElementWithSuchIdException;

    long addToPosition(LabWork labWork, int index);

    boolean replaceByID(long id, LabWork labWork);
}
