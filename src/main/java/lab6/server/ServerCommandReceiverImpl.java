package lab6.server;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import lab6.collection.LabWork;
import lab6.exceptions.NoElementWithSuchIdException;
import lab6.server.interfaces.CollectionWrapper;
import lab6.server.interfaces.ResponseBuilder;
import lab6.server.interfaces.ServerCommandReceiver;
import lab6.util.FileIO;

import java.io.IOException;
import java.util.logging.Logger;

import static lab6.util.BetterStrings.*;

public class ServerCommandReceiverImpl implements ServerCommandReceiver {
    private CollectionWrapper collectionWrapper;
    private String collectionFile;
    private ResponseBuilder responseBuilder;
    private static Logger logger = Logger.getLogger(ServerApp.class.getName());

    @Inject
    public ServerCommandReceiverImpl(@Assisted String collectionFile, CollectionWrapper collectionWrapper, ResponseBuilder responseBuilder) {
        this.collectionWrapper = collectionWrapper;
        this.collectionFile = collectionFile;
        this.responseBuilder = responseBuilder;
    }

    @Override
    public void add(LabWork labWork) {
        logger.info("Добавляем элемент в коллекцию");
        long id = collectionWrapper.add(labWork);
        responseBuilder.addLineToResponse(coloredYellow("Элемент успешно добавлен в коллекцию (id = " + id + ")."));
    }

    @Override
    public void clear() {
        logger.info("Очищаем коллекцию");
        collectionWrapper.clear();
        responseBuilder.addLineToResponse(coloredYellow("Коллекция очищена."));
    }

    @Override
    public void countLessThanDescription(String description) {
        logger.info("Добавляем в ответ количество элементов, значение поля description которых меньше " + description);
        responseBuilder.addLineToResponse(coloredYellow("Количество элементов, значение поля description которых меньше заданного: " +
                collectionWrapper.countLessThanDescription(description)));
    }

    @Override
    public void info() {
        logger.info("Добавляем в ответ информацию о коллекции");
        responseBuilder.addLineToResponse(multiline(
                "Тип коллекции: " + collectionWrapper.getCollectionType(),
                "Дата инициализации: " + collectionWrapper.getInitDate(),
                "Количество элементов: " + collectionWrapper.getSize()
        ));
    }

    @Override
    public void sort() {
        logger.info("Сортируем коллекцию");
        if (collectionWrapper.sort()) {
            responseBuilder.addLineToResponse(coloredYellow("Коллекция была успешно отсортирована в естественном порядке!"));
        } else {
            responseBuilder.addLineToResponse(coloredYellow("Коллекция пуста!"));
        }
    }

    @Override
    public void show() {
        logger.info("Добавляем в ответ содержимое коллекции");
        responseBuilder.addLineToResponse(collectionWrapper.showAll());
    }

    @Override
    public void printUniqueTunedInWorks() {
        logger.info("Добавляем в ответ уникальные значения поля tunedInWorks");
        if (collectionWrapper.isEmpty()) {
            responseBuilder.addLineToResponse(coloredYellow("Коллекция пуста!"));
        } else {
            responseBuilder.addLineToResponse("Уникальные значения поля tunedInWorks: " +
                    String.join(", ", collectionWrapper.getUniqueTunedInWorks().toString()));
        }
    }

    @Override
    public void save() {
        logger.info("Сохраняем коллекцию");
        try {
            FileIO.saveCollectionToFile(collectionFile, collectionWrapper);
        } catch (IOException e) {
            responseBuilder.addLineToResponse(coloredYellow("Сохранение не удалось!"));
        }
        responseBuilder.addLineToResponse(coloredYellow("Коллекция успешно сохранена в файл."));
    }

    @Override
    public void filterGreaterThanMinimalPoint(int minimalPoint) {
        logger.info("Добавляем в ответ элементы, значение поля minimalPoint которых больше " + minimalPoint);
        if (collectionWrapper.isEmpty()) {
            responseBuilder.addLineToResponse((coloredYellow("Коллекция пуста!")));
        } else {
            String response = collectionWrapper.filterGreaterThanMinimalPoint(minimalPoint);
            if (response.equals("")) {
                responseBuilder.addLineToResponse(
                        coloredYellow("Элементов, значение поля minimalPoint которых больше заданного, нет."));
            } else responseBuilder.addLineToResponse(response);
        }
    }

    @Override
    public void removeByID(long id) {
        logger.info("Удаляем элемент с id " + id);
        try {
            collectionWrapper.removeByID(id);
            logger.info("Элемент с id = " + id + " успешно удален");
            responseBuilder.addLineToResponse("Элемент с id = " + id + " успешно удален");
        } catch (NoElementWithSuchIdException e) {
            logger.warning("Элемента с данным id не существует в коллекции.");
            responseBuilder.addLineToResponse("Элемента с данным id не существует в коллекции.");
        }
    }

    @Override
    public void insertAt(LabWork labWork, int index) {
        logger.info("Вставляем элемент в ячейку с индексом " + index);
        long id = collectionWrapper.addToPosition(labWork, index);
        responseBuilder.addLineToResponse(coloredYellow("Элемент успешно добавлен в коллекцию (id = " + id +
                ", index = " + index + ")."));
    }

    @Override
    public void update(LabWork labWork, long id) {
        logger.info("Обновляем элемент с id " + id);
        if (collectionWrapper.replaceByID(id, labWork)) {
            logger.info("Элемент успешно заменён (id = " + id + ")");
            responseBuilder.addLineToResponse(coloredYellow("Элемент успешно заменён (id = " + id + ")"));
        } else {
            logger.info("Элемент с таким id отсутствует в коллекции!");
            responseBuilder.addLineToResponse((coloredRed("Элемент с таким id отсутствует в коллекции!")));
        }
    }
}
