package lab6.util;

import java.util.LinkedList;

/**
 * Класс для хранения ограниченного числа элементов в стеке.
 *
 * @param <E> хранимый тип данных
 */
public class LimitedStack<E> extends LinkedList<E> {
    private int sizeLimit;

    public LimitedStack(int sizeLimit) {
        this.sizeLimit = sizeLimit;
    }

    @Override
    public boolean add(E e) {
        super.addFirst(e);
        while (size() > sizeLimit) {
            super.removeLast();
        }
        return true;
    }
}
