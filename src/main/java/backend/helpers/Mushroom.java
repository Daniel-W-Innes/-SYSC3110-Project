package backend.helpers;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import java.util.HashSet;
import java.util.Set;

public final class Mushroom extends Piece {

    private final Point point;
    private final HashCode hashCode;

    Mushroom(Point point) {
        this.point = point;
        hashCode = Hashing.murmur3_128().newHasher()
                .putObject(point, point.getFunnel())
                .hash();
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
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Mushroom mushroom = (Mushroom) obj;
        return point.equals(mushroom.point);
    }

    @Override
    public int hashCode() {
        return hashCode.asInt();
    }

    @Override
    public Set<Move> getMoves(Board board) {
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
