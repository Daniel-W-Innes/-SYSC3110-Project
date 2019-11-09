package backend.models;

import backend.helpers.Move;
import backend.helpers.Piece;
import backend.helpers.Point;
import backend.helpers.Square;
import frontend.View;

import java.util.*;

public class MutableBoard extends Board implements Model {
    private final List<View> views;

    MutableBoard(Map<Point, Square> board, Map<Point, Piece> pieces, Point max) {
        super(Map.copyOf(board), max, new HashMap<>(pieces));
        views = new ArrayList<>();
    }

    public MutableBoard(MutableBoard mutableBoard) {
        super(mutableBoard.getBoard(), mutableBoard.getMax(), new HashMap<>(mutableBoard.getPieces()));
        views = new ArrayList<>(mutableBoard.getViews());
    }

    private List<View> getViews() {
        return views;
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

    @Override
    public void addView(View view) {
        views.add(view);
    }

    @Override
    public void removeView(View view) {
        views.remove(view);
    }
}
