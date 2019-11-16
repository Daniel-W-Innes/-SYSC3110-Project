package backend.helpers;

import backend.models.Model;
import com.google.common.hash.Funnel;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import frontend.View;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class Board implements Model {
    private final Map<Point, Square> board;
    private final Point max;
    private final Map<Point, Piece> pieces;
    private final Boolean isVictory;
    private final HashCode hashCode;

    private Board(Map<Point, Square> board, Map<Point, Piece> pieces, Point max) {
        this.board = Map.copyOf(board);
        this.max = max;
        this.pieces = Map.copyOf(pieces);
        isVictory = getPieces().entrySet().stream()
                .filter(entry -> entry.getValue() instanceof Rabbit)
                .allMatch(entry -> hasSquare(entry.getKey()) && getSquare(entry.getKey()).isHole());
        hashCode = Hashing.murmur3_128().newHasher()
                .putInt(board.hashCode())
                .putInt(pieces.hashCode())
                .hash();
    }

    private Map<Point, Square> getBoard() {
        return board;
    }

    public Map<Point, Piece> getPieces() {
        return pieces;
    }

    public Point getMax() {
        return max;
    }

    private boolean hasSquare(Point point) {
        return getBoard().containsKey(point);
    }

    private Piece getPiece(Point point) {
        return getPieces().get(point);
    }

    private Square getSquare(Point point) {
        return getBoard().get(point);
    }

    boolean hasPiece(Point point) {
        return getPieces().containsKey(point);
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
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public boolean isVictory() {
        return isVictory;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Board board = (Board) obj;
        return this.board.equals(board.board) && pieces.equals(board.pieces);
    }

    public Funnel<Board> getFunnel() {
        return (from, into) -> into.putInt(from.board.hashCode()).putInt(from.pieces.hashCode());
    }

    @Override
    public int hashCode() {
        return hashCode.asInt();
    }

    @Override
    public void setView(View v) {

    }

    static class Builder {
        private final Set<Point> holes;
        private final Set<Point> raisedSquares;
        private final Map<Point, Piece> pieces;


        Builder(boolean useDefaultMap) {
            if (useDefaultMap) {
                holes = Set.of(new Point(0, 0), new Point(4, 4), new Point(0, 4), new Point(4, 0), new Point(2, 2));
                raisedSquares = Set.of(new Point(0, 2), new Point(2, 0), new Point(2, 4), new Point(4, 2));
            } else {
                holes = new HashSet<>();
                raisedSquares = new HashSet<>();
            }
            pieces = new HashMap<>();
        }

        Builder addHole(Point point) {
            holes.add(point);
            return this;
        }

        Builder addRaisedSquares(Point point) {
            raisedSquares.add(point);
            return this;
        }

        Builder addPiece(Piece piece) {
            piece.occupies().stream()
                    .map(point -> Map.entry(point, piece))
                    .forEach(entry -> pieces.put(entry.getKey(), entry.getValue()));
            return this;
        }

        private Point updateMax(Point max, Point point) {
            if (point.x > max.x) {
                max = new Point(point.x, max.y);
            }
            if (point.y > max.y) {
                max = new Point(max.x, point.y);
            }
            return max;
        }


        Board build() {
            Point max = new Point(0, 0);
            Map<Point, Square> board = new HashMap<>();
            for (Point point : raisedSquares) {
                board.put(point, new Square(false));
                max = updateMax(max, point);
            }
            for (Point point : holes) {
                board.put(point, new Square(true));
                max = updateMax(max, point);
            }
            for (Point point : pieces.keySet()) {
                max = updateMax(max, point);
            }
            return new Board(board, pieces, max);
        }
    }

    public static class Mover {
        private final Board board;
        private final Map<Point, Piece> pieces;

        public Mover(Board board) {
            this.board = board;
            pieces = new HashMap<>(board.getPieces());
        }

        public Mover movePiece(Move move) {
            move.getStart().occupies().forEach(pieces::remove);
            move.getEnd().occupies().stream()
                    .map(point -> Map.entry(point, move.getEnd()))
                    .forEach(entry -> pieces.put(entry.getKey(), entry.getValue()));
            return this;
        }

        public Board build() {
            return new Board(board.board, pieces, board.max);
        }
    }

}
