package backend.helpers;

import java.util.HashMap;
import java.util.Map;

public class MutableBoard extends Board {

    MutableBoard(Map<Point, Square> board, Map<Point, Piece> pieces, Point max) {
        super(Map.copyOf(board), max, new HashMap<>(pieces));
    }

    public MutableBoard(MutableBoard mutableBoard) {
        super(mutableBoard.getBoard(), mutableBoard.getMax(), new HashMap<>(mutableBoard.getPieces()));
    }

    public void setPieces(Map<Point, Piece> piece) {
        getPieces().clear();
        getPieces().putAll(piece);
    }

    public void addPiece(Piece piece) {
        piece.occupies().forEach(point -> getPieces().put(point, piece));
    }

    public void movePiece(Move move) {
        move.getStart().occupies().forEach(getPieces()::remove);
        move.getEnd().occupies().stream()
                .map(point -> Map.entry(point, move.getEnd()))
                .forEach(entry -> getPieces().put(entry.getKey(), entry.getValue()));
        //TODO draw
    }

    public ImmutableBoard getImmutableBoard() {
        return new ImmutableBoard(getBoard(), getPieces(), getMax());
    }
}
