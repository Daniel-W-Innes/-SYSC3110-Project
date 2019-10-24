package helpers;

import java.awt.*;
import java.util.Arrays;

public class Move {
    private final Point start;
    private final Point end;
    private final int hashcode;

    public Move(Point start, Point end) {
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
}
