package model;

import helpers.Move;
import helpers.Piece;
import helpers.Rabbit;
import helpers.Square;

import java.awt.*;
import java.util.*;

public class Board {
    private final Map<Point, Square> board;
    private final Point max;
    private final Map<Point, Piece> pieces;

    private Board(Map<Point, Square> board, Map<Point, Piece> pieces, Point max) {
        this.board = Collections.unmodifiableMap(board);
        this.pieces = new HashMap<>(pieces);
        this.max = max;
    }

    public Board(Board board) {
        this.board = board.getBoard();
        pieces = new HashMap<>(board.getPieces());
        max = board.getMax();
    }

    private Map<Point, Square> getBoard() {
        return board;
    }

    public void addPiece(Point point, Piece piece) {
        pieces.put(point, piece);
    }

    public Map<Point, Piece> getPieces() {
        return pieces;
    }

    private void movePiece(Move move) {
        pieces.put(move.getEnd(), pieces.get(move.getStart()));
        pieces.remove(move.getStart());
    }

    public boolean hasPiece(Point point) {
        return pieces.containsKey(point);
    }

    public Point getMax() {
        return max;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Point point;
        for (int x = 0; x <= max.x; x++) {
            for (int y = 0; y <= max.y; y++) {
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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Board board = (Board) obj;
        return getBoard().equals(board.getBoard()) && getPieces().equals(board.getPieces());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new int[]{board.hashCode(), pieces.hashCode()});
    }

    private Piece getPiece(Point point) {
        return pieces.get(point);
    }

    private boolean hasSquare(Point point) {
        return board.containsKey(point);
    }

    private Square getSquare(Point point) {
        return board.get(point);
    }

    public void movePieces(Set<Move> moves) {
        for (Move move : moves) {
            movePiece(move);
        }
    }

    public boolean isVictory() {
        return pieces.entrySet().stream()
                .filter(entry -> entry.getValue() instanceof Rabbit)
                .allMatch(entry -> hasSquare(entry.getKey()) && getSquare(entry.getKey()).isHole());
    }

    public static class Builder {
        private final Set<Point> holes;
        private final Set<Point> raisedSquares;
        private final Map<Point, Piece> pieces;


        public Builder(boolean useDefaultMap) {
            if (useDefaultMap) {
                holes = Set.of(new Point(0, 0), new Point(4, 4), new Point(0, 4), new Point(4, 0), new Point(2, 2));
                raisedSquares = Set.of(new Point(0, 2), new Point(2, 0), new Point(2, 4), new Point(4, 2));
            } else {
                holes = new HashSet<>();
                raisedSquares = new HashSet<>();
            }
            pieces = new HashMap<>();
        }

        public Builder addHole(Point point) {
            holes.add(point);
            return this;
        }

        public Builder addRaisedSquares(Point point) {
            raisedSquares.add(point);
            return this;
        }

        public Builder addPieces(Point loc, Piece piece) {
            pieces.put(loc, piece);
            return this;
        }

        private void updateMax(Point max, Point point) {
            if (point.x > max.x) {
                max.x = point.x;
            }
            if (point.y > max.y) {
                max.y = point.y;
            }
        }

        public Board build() {
            Point max = new Point(0, 0);
            Map<Point, Square> board = new HashMap<>();
            for (Point point : raisedSquares) {
                board.put(point, new Square(false, true));
                updateMax(max, point);
            }
            for (Point point : holes) {
                board.put(point, new Square(true, true));
                updateMax(max, point);
            }
            pieces.keySet().forEach(point -> updateMax(max, point));
            return new Board(board, pieces, max);
        }
    }
}
