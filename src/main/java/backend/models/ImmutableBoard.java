package backend.models;

import backend.helpers.Piece;
import backend.helpers.Point;
import backend.helpers.Square;

import java.util.Arrays;
import java.util.Map;

public final class ImmutableBoard extends Board {

    ImmutableBoard(Map<Point, Square> board, Map<Point, Piece> pieces, Point max) {
        super(Map.copyOf(board), max, Map.copyOf(pieces));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Board)) {
            return false;
        }
        Board board = (Board) obj;
        return getBoard().equals(board.getBoard()) && getPieces().equals(board.getPieces());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new int[]{getBoard().hashCode(), getPieces().hashCode()});
    }
}
