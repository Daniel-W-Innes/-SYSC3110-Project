package backend.helpers;

import java.util.Map;

public final class ImmutableBoard extends Board {

    public ImmutableBoard(Map<Point, Square> board, Map<Point, Piece> pieces, Point max) {
        super(Map.copyOf(board), max, Map.copyOf(pieces));
    }

    public MutableBoard getMutableBoard() {
        return new MutableBoard(getBoard(), getPieces(), getMax());
    }
}
