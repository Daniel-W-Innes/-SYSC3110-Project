package backend.models;

import backend.helpers.Piece;
import backend.helpers.Point;
import backend.helpers.Square;

import java.util.Map;

@FunctionalInterface
interface BoardConstructor {
    Board build(Map<Point, Square> board, Map<Point, Piece> pieces, Point max);
}
