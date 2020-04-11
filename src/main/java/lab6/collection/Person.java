package lab6.collection;

import lab6.exceptions.LabWorkFieldException;

import static lab6.util.BetterStrings.*;

/**
 * Класс человека-автора лаб. работы
 */
public class Person {
    private String name; // not empty, not null
    private Float height; // > 0, null
    private String passportID; // length >= 9, not null
    private Color hairColor; // null
    private Location location; // not null

    public Person() {
        location = new Location();
    }

    public Person(String name, Float height, String passportID, Color hairColor, Location location) {
        this.name = name;
        this.height = height;
        this.passportID = passportID;
        this.hairColor = hairColor;
        this.location = location;
    }

    @Override
    public String toString() {
        return multiline("Имя: " + name,
                "Рост: " + blueStringIfNull(height),
                "Номер паспорта: " + passportID,
                "Цвет волос: " + blueStringIfNull(hairColor),
                location.toString());
    }

    public void setLocation(Integer x, float y, Integer z) throws LabWorkFieldException {
        if (x == null || z == null) throw new LabWorkFieldException();
        location.setX(x);
        location.setY(y);
        location.setZ(z);
    }

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws LabWorkFieldException {
        if (name == null || name.equals("")) throw new LabWorkFieldException();
        this.name = name;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) throws LabWorkFieldException {
        if (height != null && height <= 0) throw new LabWorkFieldException();
        this.height = height;
    }

    public String getPassportID() {
        return passportID;
    }

    public void setPassportID(String passportID) throws LabWorkFieldException {
        if (passportID == null || passportID.equals("") || passportID.length() < 9) throw new LabWorkFieldException();
        this.passportID = passportID;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public void setHairColor(Color hairColor) {
        // Нет условий
        this.hairColor = hairColor;
    }
}
