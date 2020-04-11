package lab6.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lab6.collection.LabWork;
import lab6.exceptions.NoElementWithSuchIdException;
import lab6.server.interfaces.ClientIO;
import lab6.server.interfaces.CollectionWrapper;
import lab6.server.interfaces.ServerCommandReceiver;
import lab6.util.FileIO;

import java.io.IOException;

import static lab6.util.BetterStrings.*;
import static lab6.util.BetterStrings.coloredYellow;

@Singleton
public class ServerCommandReceiverImpl implements ServerCommandReceiver {
    private CollectionWrapper collectionWrapper;
    private String collectionFile;
    private ClientIO clientIO;

    @Inject
    public ServerCommandReceiverImpl(CollectionWrapper collectionWrapper, String collectionFile) {
        this.collectionWrapper = collectionWrapper;
        this.collectionFile = collectionFile;
    }

    @Override
    public void setCollectionFile(String collectionFile) {
        this.collectionFile = collectionFile;
    }

    @Override
    public void add(LabWork labWork) {
        long id = collectionWrapper.add(labWork);
        clientIO.printToClient(coloredYellow("Элемент успешно добавлен в коллекцию (id = " + id + ")."));
    }

    @Override
    public void clear() {
        collectionWrapper.clear();
        clientIO.printToClient(coloredYellow("Коллекция очищена."));
    }

    @Override
    public void countLessThanDescription(String description) {
        clientIO.printToClient(coloredYellow("Количество элементов, значение поля description которых меньше заданного: " +
                collectionWrapper.countLessThanDescription(description)));
    }

    @Override
    public void info() {
        clientIO.printToClient(multiline(
                "Тип коллекции: " + collectionWrapper.getCollectionType(),
                "Дата инициализации: " + collectionWrapper.getInitDate(),
                "Количество элементов: " + collectionWrapper.getSize()
        ));
    }

    @Override
    public void sort() {
        if (collectionWrapper.sort()) {
            clientIO.printToClient(coloredYellow("Коллекция была успешно отсортирована в естественном порядке!"));
        } else {
            clientIO.printToClient(coloredYellow("Коллекция пуста!"));
        }
    }

    @Override
    public void show() {
        clientIO.printToClient(collectionWrapper.showAll());
    }

    @Override
    public void printUniqueTunedInWorks() {
        if (collectionWrapper.isEmpty()) {
            clientIO.printToClient(coloredYellow("Коллекция пуста!"));
        } else {
            clientIO.printToClient("Уникальные значения поля tunedInWorks: " +
                    String.join(", ", collectionWrapper.getUniqueTunedInWorks().toString()));
        }
    }

    @Override
    public void save() {
        try {
            FileIO.saveCollectionToFile(collectionFile, collectionWrapper);
        } catch (IOException e) {
            clientIO.printToClient(coloredYellow("Сохранение не удалось!"));
        }
        clientIO.printToClient(coloredYellow("Коллекция успешно сохранена в файл."));
    }

    @Override
    public void filterGreaterThanMinimalPoint(int minimalPoint) {
        if (collectionWrapper.isEmpty()) {
            clientIO.printToClient((coloredYellow("Коллекция пуста!")));
        } else {
            if (!collectionWrapper.filterGreaterThanMinimalPoint(minimalPoint)) {
                clientIO.printToClient(coloredYellow("Элементов, значение поля minimalPoint которых больше заданного, нет."));
            }
        }
    }

    @Override
    public void removeByID(long id) {
        try {
            collectionWrapper.removeByID(id);
            clientIO.printToClient("Элемент с id = " + id + " успешно удален");
        } catch (NoElementWithSuchIdException e) {
            clientIO.printToClient("Элемента с данным id не существует в коллекции.");
        }
    }

    @Override
    public void insertAt(LabWork labWork, int index) {
        long id = collectionWrapper.addToPosition(labWork, index);
        clientIO.printToClient(coloredYellow("Элемент успешно добавлен в коллекцию (id = " + id +
                ", index = " + index + ")."));
    }

    @Override
    public void update(LabWork labWork, long id) {
        if (collectionWrapper.replaceByID(id, labWork))
            clientIO.printToClient(coloredYellow("Элемент успешно заменён (id = " + id + ")."));
        else {
//            if (executingScripts.isEmpty())
//                System.out.println(BetterStrings.coloredRed("Элемент с таким id отсутствует в коллекции!"));
//            else throw new RuntimeException();
            clientIO.printToClient((coloredRed("Элемент с таким id отсутствует в коллекции!")));
        }
    }
}
