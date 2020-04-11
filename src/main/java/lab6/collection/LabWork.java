package lab6.collection;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import lab6.exceptions.LabWorkFieldException;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static lab6.util.BetterStrings.blueStringIfNull;
import static lab6.util.BetterStrings.multiline;


/**
 * Класс лабораторной работы (элемента коллекции)
 */
public class LabWork implements Comparable<LabWork> {
    private static final int NUMBER_OF_FIELDS = 14;
    private Long id; // > 0, unique, auto-gen, not null
    private String name; // not null
    private Coordinates coordinates; // not null
    private LocalDateTime creationDate; // auto-gen, not null
    private Integer minimalPoint; // > 0, null
    private String description; // not null
    private Integer tunedInWorks; // null
    private Difficulty difficulty; // not null
    private Person author; // null

    public LabWork() {
        author = new Person();
    }

    public LabWork(String name, Coordinates coordinates, Integer minimalPoint, String description, Integer tunedInWorks, Difficulty difficulty, Person author) {
        this.name = name;
        this.coordinates = coordinates;
        this.minimalPoint = minimalPoint;
        this.description = description;
        this.creationDate = LocalDateTime.now();
        this.tunedInWorks = tunedInWorks;
        this.difficulty = difficulty;
        this.author = author;
    }

    public static int getNumberOfFields() {
        return NUMBER_OF_FIELDS;
    }

    @Override
    public String toString() {
        return multiline(
                "ID: " + id,
                "Имя: " + name,
                coordinates.toString(),
                "Дата создания: " + creationDate.withNano(0),
                "minimalPoint: " + blueStringIfNull(minimalPoint),
                "Описание: " + description,
                "tunedInWorks: " + blueStringIfNull(tunedInWorks),
                "Сложность: " + difficulty.name(),
                author.toString() + '\n'
        );
    }

    public void safeSetId(Long id) {
        this.id = id;
    }

    public void preSetId(Long id) throws LabWorkFieldException {
        if (id == null || id < 0) throw new LabWorkFieldException();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws LabWorkFieldException {
        if (description == null || description.equals("")) throw new LabWorkFieldException();
        this.description = description;
    }

    public Integer getTunedInWorks() {
        return tunedInWorks;
    }

    public void setTunedInWorks(Integer tunedInWorks) {
        // Нет условий
        this.tunedInWorks = tunedInWorks;
    }

    public Integer getMinimalPoint() {
        return minimalPoint;
    }

    public void setMinimalPoint(Integer minimalPoint) throws LabWorkFieldException {
        if (minimalPoint != null && minimalPoint <= 0) throw new LabWorkFieldException();
        this.minimalPoint = minimalPoint;
    }

    public void setName(String name) throws LabWorkFieldException {
        if (name == null || name.equals("")) throw new LabWorkFieldException();
        this.name = name;
    }

    public void setCoordinates(long x, Float y) throws LabWorkFieldException {
        if (y == null || x > 625) throw new LabWorkFieldException();
        coordinates = new Coordinates();
        this.coordinates.setX(x);
        this.coordinates.setY(y);
    }

    public void setDifficulty(Difficulty difficulty) throws LabWorkFieldException {
        if (difficulty == null) throw new LabWorkFieldException();
        this.difficulty = difficulty;
    }

    public Person getAuthor() {
        return author;
    }

    private String[] toArray() {
        ArrayList<String> array = new ArrayList<>(getStringValues(
                name,
                coordinates.getX(),
                coordinates.getY(),
                minimalPoint,
                description,
                tunedInWorks,
                difficulty,
                author.getName(),
                author.getHeight(),
                author.getPassportID(),
                author.getHairColor(),
                author.getLocation().getX(),
                author.getLocation().getY(),
                author.getLocation().getZ(),
                id,
                creationDate.withNano(0)
        ));
        String[] result = new String[array.size()];
        result = array.toArray(result);
        return result;
    }

    private ArrayList<String> getStringValues(Object... objects) {
        ArrayList<String> array = new ArrayList<>();
        for (Object object : objects) {
            if (object == null) array.add("");
            else array.add(String.valueOf(object));
        }
        return array;
    }

    @Override
    public int compareTo(LabWork o) {
        return (int) (id - o.getId());
    }

    public String toCSVline() {
        CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).withQuoteChar('"').build();
        return parser.parseToLine(toArray(), false);
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
