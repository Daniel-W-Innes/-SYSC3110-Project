package model;

import java.awt.*;
import java.util.Arrays;

public class ImmutablePoint {
    private final int x;
    private final int y;
    private final int hashCode;


    ImmutablePoint(Point point) {
        this.x = (int) point.getX();
        this.y = (int) point.getY();
        hashCode = Arrays.hashCode((new int[]{x, y}));
    }

    public ImmutablePoint(int x, int y) {
        this.x = x;
        this.y = y;
        hashCode = Arrays.hashCode((new int[]{x, y}));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    Point getMutablePoint() {
        return new Point(getX(), getY());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        ImmutablePoint immutablePoint = (ImmutablePoint) obj;
        return getX() == immutablePoint.getX() && getY() == immutablePoint.getY();
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        return "{" + x + ", " + y + "}";
    }
}
