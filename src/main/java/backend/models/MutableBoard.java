package backend.models;

import backend.helpers.*;
import frontend.View;

import java.util.*;

public class MutableBoard extends Board implements Model {
    private final List<View> views;

    MutableBoard(Map<Point, Square> board, Map<Point, Piece> pieces, Point max) {
        super(Collections.unmodifiableMap(board), max, new HashMap<>(pieces));
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

    public boolean hasPiece(Point point) {
        return getPieces().containsKey(point);
    }

    public Point getMax() {
        return super.getMax();
    }

    public ImmutableBoard getImmutableBoard() {
        return new ImmutableBoard(new HashMap<>(getBoard()), new HashMap<>(getPieces()), getMax());
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Point point;
        for (int x = 0; x <= getMax().x; x++) {
            for (int y = 0; y <= getMax().y; y++) {
                point = new Point(x, y);
                stringBuilder.append('|');
                stringBuilder.append(hasSquare(point) ? getSquare(point).toString() : "_");
                stringBuilder.append(hasPiece(point) ? getPiece(point).toString() : "_");
            }
            stringBuilder.append('|');
//            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        MutableBoard mutableBoard = (MutableBoard) obj;
        return getBoard().equals(mutableBoard.getBoard()) && getPieces().equals(mutableBoard.getPieces());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new int[]{getBoard().hashCode(), getPieces().hashCode()});
    }

    private Piece getPiece(Point point) {
        return getPieces().get(point);
    }

    private boolean hasSquare(Point point) {
        return getBoard().containsKey(point);
    }

    private Square getSquare(Point point) {
        return getBoard().get(point);
    }

    public boolean isVictory() {
        return getPieces().entrySet().stream()
                .filter(entry -> entry.getValue() instanceof Rabbit)
                .allMatch(entry -> hasSquare(entry.getKey()) && getSquare(entry.getKey()).isHole());
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
