package backend.helpers;

import backend.models.ImmutableBoard;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Mushroom implements Piece {

    private final Point point;

    Mushroom(Point point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "M";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Mushroom) {
            return super.equals(obj) && point.equals(((Mushroom) obj).point);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new int[]{super.hashCode(), point.hashCode()});
    }

    @Override
    public Set<Move> getMoves(ImmutableBoard board, Point point) {
        return new HashSet<>();
    }

    @Override
    public boolean occupies(Point point) {
        return this.point.equals(point);
    }

    @Override
    public Set<Point> occupies() {
        return Set.of(point);
    }
}
