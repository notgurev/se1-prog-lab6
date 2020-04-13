package lab6.collection;

import java.io.Serializable;

import static lab6.util.BetterStrings.multiline;

/**
 * Класс местоположения Person
 */
public class Location implements Serializable {
    private Integer x; // not null
    private float y; // not null
    private Integer z; // not null

    public Location() {
    }

    public Location(Integer x, float y, Integer z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return multiline(
                "Местоположение по X: " + x,
                "Местоположение по Y: " + y,
                "Местоположение по Z: " + z
        );
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Integer getZ() {
        return z;
    }

    public void setZ(Integer z) {
        this.z = z;
    }
}
