package backend.helpers;

import java.util.Set;

public interface Piece {

    Set<Move> getMoves(ImmutableBoard board,  Point point);

    boolean occupies(Point point);

    Set<Point> occupies();
}
