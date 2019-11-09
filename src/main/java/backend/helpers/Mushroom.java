package backend.helpers;

import backend.models.ImmutableBoard;

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
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Mushroom)) {
            return false;
        }
        Mushroom mushroom = (Mushroom) obj;
        return point.equals(mushroom.point);
    }

    @Override
    public int hashCode() {
        return point.hashCode();
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
