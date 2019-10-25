package helpers;

import java.awt.*;
import java.util.Arrays;

public class Move {
    private final Point start;
    private final Point end;
    private final int hashcode;

    Move(Point start, Point end) {
        this.start = start;
        this.end = end;
        hashcode = Arrays.hashCode((new int[]{start.hashCode(), end.hashCode()}));
    }

    public Point getEnd() {
        return end;
    }

    public Point getStart() {
        return start;
    }

    public Move getReverse() {
        return new Move(getEnd(), getStart());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Move move = (Move) obj;
        return getStart().equals(move.getStart()) && getEnd().equals(move.getEnd());
    }

    @Override
    public int hashCode() {
        return hashcode;
    }

    @Override
    public String toString() {
        return "{" + getStart().x + " , " + getStart().y + "} -> {" + getEnd().x + " , " + getEnd().y + "}";
    }
}
