package backend.helpers;

import java.util.Map;

@FunctionalInterface
interface BoardConstructor {
    Board build(Map<Point, Square> board, Map<Point, Piece> pieces, Point max);
}
