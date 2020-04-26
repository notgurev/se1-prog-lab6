package se1_prog_lab.collection;


import java.io.Serializable;

import static se1_prog_lab.util.BetterStrings.multiline;

/**
 * Класс координат лаб. работы (?)
 */
public class Coordinates implements Serializable {
    private long x; // max = 625
    private Float y; // not null

    public Coordinates() {
    }

    public Coordinates(long x, Float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return multiline("Координата X: " + x, "Координата Y: " + y);
    }

    public long getX() {
        return x;
    }

    public void setX(long x) {
        this.x = x;
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }
}
