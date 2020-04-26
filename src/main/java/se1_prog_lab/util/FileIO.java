package se1_prog_lab.util;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import se1_prog_lab.exceptions.LabWorkFieldException;
import se1_prog_lab.server.interfaces.CollectionWrapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Класс для чтения и записи в файл в формате CSV
 */
public class FileIO {
    private static final Logger logger = Logger.getLogger(FileIO.class.getName());

    /**
     * Метод, считывающий коллекцию из файла
     *
     * @param pathToFile       путь к файлу
     * @param targetCollection коллекция, в которую нужно записать считанные данные
     */
    public static void readCollectionFromFile(String pathToFile, CollectionWrapper targetCollection) {
        ArrayList<Integer> linesWithErrors = new ArrayList<>();
        try (FileReader fileReader = new FileReader(pathToFile); CSVReader csvReader = new CSVReader(fileReader)) {
            List<String[]> lines = csvReader.readAll();
            int line = 0;
            for (String[] string : lines) {
                if (string[0].trim().equals("")) continue;
                line++;
                try {
                    targetCollection.add(ElementCreator.fromStringArray(string, true));
                } catch (LabWorkFieldException | ArrayIndexOutOfBoundsException | NumberFormatException e) {
                    linesWithErrors.add(line);
                }
            }
            logger.info("Коллекция успешно загружена!");
        } catch (FileNotFoundException e) {
            logger.warning("Файл не найден!");
        } catch (IOException e) {
            logger.warning("Что-то пошло не так при чтении файла!");
        } catch (CsvException e) {
            logger.warning("Парсер не смог прочитать коллекцию!");
        } catch (Exception e) {
            logger.warning("Что-то пошло не так во время считывания коллекции из файла.");
        }
        if (!linesWithErrors.isEmpty()) {
            logger.warning("Следующие строки неправильно хранят элеменент коллекции, поэтому не были прочитаны: "
                    + linesWithErrors.toString());
        }
        if (targetCollection.isEmpty()) {
            logger.info("Коллекция не была загружена из файла, создаем пустую.");
        }
    }

    /**
     * Метод, сохраняющий коллекцию в файл
     *
     * @param fileName   путь к файлу
     * @param collection коллекция, которую нужно сохранить
     * @throws IOException если что-то пошло не так при сохранении
     */
    public static void saveCollectionToFile(String fileName, CollectionWrapper collection) throws IOException {
        try (BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(fileName))) {
            for (String s : collection.toArray()) {
                s = s + '\n';
                output.write(s.getBytes());
            }
        }
    }
}
