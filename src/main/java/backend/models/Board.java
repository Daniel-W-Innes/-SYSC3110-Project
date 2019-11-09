package backend.models;

import backend.helpers.Piece;
import backend.helpers.Point;
import backend.helpers.Rabbit;
import backend.helpers.Square;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Board {
    private final Map<Point, Square> board;
    private final Point max;
    private final Map<Point, Piece> pieces;
    private Boolean isVictory;

    Board(Map<Point, Square> board, Point max, Map<Point, Piece> pieces) {
        this.board = board;
        this.max = max;
        this.pieces = pieces;
    }

    Map<Point, Square> getBoard() {
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

    public boolean hasPiece(Point point) {
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
        if (isVictory == null) {
            isVictory = getPieces().entrySet().stream()
                    .filter(entry -> entry.getValue() instanceof Rabbit)
                    .allMatch(entry -> hasSquare(entry.getKey()) && getSquare(entry.getKey()).isHole());
        }
        return isVictory;
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

        private Point updateMax(Point max, Point point) {
            if (point.x > max.x) {
                max = new Point(point.x, max.y);
            }
            if (point.y > max.y) {
                max = new Point(max.x, point.y);
            }
            return max;
        }


        private Board build(BoardConstructor function) {
            Point max = new Point(0, 0);
            Map<Point, Square> board = new HashMap<>();
            for (Point point : raisedSquares) {
                board.put(point, new Square(false, true));
                max = updateMax(max, point);
            }
            for (Point point : holes) {
                board.put(point, new Square(true, true));
                max = updateMax(max, point);
            }
            for (Point point : pieces.keySet()) {
                max = updateMax(max, point);
            }
            return function.build(board, pieces, max);
        }


        public ImmutableBoard buildImmutableBoard() {
            return (ImmutableBoard) build(ImmutableBoard::new);
        }

        public MutableBoard buildMutableBoard() {
            return (MutableBoard) build(MutableBoard::new);
        }
    }

}
