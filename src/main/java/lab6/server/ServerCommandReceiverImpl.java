package lab6.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import lab6.collection.LabWork;
import lab6.exceptions.NoElementWithSuchIdException;
import lab6.server.interfaces.ResponseBuilder;
import lab6.server.interfaces.CollectionWrapper;
import lab6.server.interfaces.ServerCommandReceiver;
import lab6.util.FileIO;

import java.io.IOException;

import static lab6.util.BetterStrings.*;
import static lab6.util.BetterStrings.coloredYellow;

public class ServerCommandReceiverImpl implements ServerCommandReceiver {
    private CollectionWrapper collectionWrapper;
    private String collectionFile;
    private ResponseBuilder responseBuilder;

    @Inject
    public ServerCommandReceiverImpl(@Assisted String collectionFile, CollectionWrapper collectionWrapper, ResponseBuilder responseBuilder) {
        this.collectionWrapper = collectionWrapper;
        this.collectionFile = collectionFile;
        this.responseBuilder = responseBuilder;
    }

    @Override
    public void setCollectionFile(String collectionFile) {
        this.collectionFile = collectionFile;
    }

    @Override
    public void add(LabWork labWork) {
        long id = collectionWrapper.add(labWork);
        responseBuilder.addLineToResponse(coloredYellow("Элемент успешно добавлен в коллекцию (id = " + id + ")."));
    }

    @Override
    public void clear() {
        collectionWrapper.clear();
        responseBuilder.addLineToResponse(coloredYellow("Коллекция очищена."));
    }

    @Override
    public void countLessThanDescription(String description) {
        responseBuilder.addLineToResponse(coloredYellow("Количество элементов, значение поля description которых меньше заданного: " +
                collectionWrapper.countLessThanDescription(description)));
    }

    @Override
    public void info() {
        responseBuilder.addLineToResponse(multiline(
                "Тип коллекции: " + collectionWrapper.getCollectionType(),
                "Дата инициализации: " + collectionWrapper.getInitDate(),
                "Количество элементов: " + collectionWrapper.getSize()
        ));
    }

    @Override
    public void sort() {
        if (collectionWrapper.sort()) {
            responseBuilder.addLineToResponse(coloredYellow("Коллекция была успешно отсортирована в естественном порядке!"));
        } else {
            responseBuilder.addLineToResponse(coloredYellow("Коллекция пуста!"));
        }
    }

    @Override
    public void show() {
        responseBuilder.addLineToResponse(collectionWrapper.showAll());
    }

    @Override
    public void printUniqueTunedInWorks() {
        if (collectionWrapper.isEmpty()) {
            responseBuilder.addLineToResponse(coloredYellow("Коллекция пуста!"));
        } else {
            responseBuilder.addLineToResponse("Уникальные значения поля tunedInWorks: " +
                    String.join(", ", collectionWrapper.getUniqueTunedInWorks().toString()));
        }
    }

    @Override
    public void save() {
        try {
            FileIO.saveCollectionToFile(collectionFile, collectionWrapper);
        } catch (IOException e) {
            responseBuilder.addLineToResponse(coloredYellow("Сохранение не удалось!"));
        }
        responseBuilder.addLineToResponse(coloredYellow("Коллекция успешно сохранена в файл."));
    }

    @Override
    public void filterGreaterThanMinimalPoint(int minimalPoint) {
        if (collectionWrapper.isEmpty()) {
            responseBuilder.addLineToResponse((coloredYellow("Коллекция пуста!")));
        } else {
            if (!collectionWrapper.filterGreaterThanMinimalPoint(minimalPoint)) {
                responseBuilder.addLineToResponse(coloredYellow("Элементов, значение поля minimalPoint которых больше заданного, нет."));
            }
        }
    }

    @Override
    public void removeByID(long id) {
        try {
            collectionWrapper.removeByID(id);
            responseBuilder.addLineToResponse("Элемент с id = " + id + " успешно удален");
        } catch (NoElementWithSuchIdException e) {
            responseBuilder.addLineToResponse("Элемента с данным id не существует в коллекции.");
        }
    }

    @Override
    public void insertAt(LabWork labWork, int index) {
        long id = collectionWrapper.addToPosition(labWork, index);
        responseBuilder.addLineToResponse(coloredYellow("Элемент успешно добавлен в коллекцию (id = " + id +
                ", index = " + index + ")."));
    }

    @Override
    public void update(LabWork labWork, long id) {
        if (collectionWrapper.replaceByID(id, labWork))
            responseBuilder.addLineToResponse(coloredYellow("Элемент успешно заменён (id = " + id + ")."));
        else {
//            if (executingScripts.isEmpty())
//                System.out.println(BetterStrings.coloredRed("Элемент с таким id отсутствует в коллекции!"));
//            else throw new RuntimeException();
            responseBuilder.addLineToResponse((coloredRed("Элемент с таким id отсутствует в коллекции!")));
        }
    }
}
