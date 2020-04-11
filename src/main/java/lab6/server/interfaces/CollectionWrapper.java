package lab6.server.interfaces;

import lab6.exceptions.NoElementWithSuchIdException;

import java.util.Set;

public interface CollectionWrapper {
    long add(Object object);

    boolean isEmpty();

    String[] toArray();

    void clear();

    String countLessThanDescription(String description);

    String getCollectionType();

    String getSize();

    String getInitDate();

    boolean sort();

    String showAll();

    Set<Integer> getUniqueTunedInWorks();

    boolean filterGreaterThanMinimalPoint(int minimalPoint);

    void removeByID(long id) throws NoElementWithSuchIdException;

    long addToPosition(Object carriedObject, int index);

    boolean replaceByID(long id, Object carriedObject);
}
