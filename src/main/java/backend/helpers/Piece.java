package backend.helpers;

import java.util.Set;

public interface Piece {

    Set<Move> getMoves(Board board, Point point);

    boolean occupies(Point point);

    Set<Point> occupies();
}
