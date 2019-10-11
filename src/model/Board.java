package model;

import java.awt.*;
import java.util.*;

public class Board implements Iterable<Map.Entry<Point, Square>> {

    public static class Copier {
        private Map<Point, Square> board;
        private Point max;
        private boolean isVictory;
        private boolean isVictoryValid;
        private boolean isMaxValid;

        public Copier(Board board) {
            this.board = new HashMap<>(board.getBoard());
            max = board.getMax();
            isVictory = board.isVictory();
            isMaxValid = true;
            isVictoryValid = true;
        }

        public Copier removePieces(Point loc) {
            Square square = board.get(loc);
            if (square.isTunnel()) {
                board.put(loc, new Square(true, true, null));
            } else if (square.isRaised()) {
                board.put(loc, new Square(false, true, null));
            } else {
                board.remove(loc);
                if (max.x == loc.x || max.y == loc.y) {
                    isMaxValid = false;
                }
            }
            if (!square.isTunnel() && !isVictory && square.getPiece() == Piece.RABBIT) {
                isVictoryValid = false;
            }
            return this;

        }

        public Copier addPieces(Point loc, Piece piece) {
            if (board.containsKey(loc)) {
                Square square = board.get(loc);
                if (!square.hasPiece()) {
                    board.put(loc, new Square(square.isTunnel(), square.isRaised(), piece));
                    if (!square.isTunnel() && piece == Piece.RABBIT) {
                        isVictory = false;
                    }
                }
            } else {
                board.put(loc, new Square(false, false, piece));
                updateMax(max, loc);
            }
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
            if (!isMaxValid) {
                for (Point point : board.keySet()) {
                    updateMax(max, point);
                }
            }
            if (!isVictoryValid) {
                isVictory = true;
                for (Square square : board.values()) {
                    if (square.getPiece() == Piece.RABBIT && !square.isTunnel()) {
                        isVictory = false;
                        break;
                    }
                }
            }
            return new Board(board, max, isVictory);
        }
    }

    public static class Builder {
        private final Set<Point> tunnels;
        private final Set<Point> raisedSquares;
        private final Map<Point, Piece> pieces;

        public Builder() {
            tunnels = new HashSet<>();
            pieces = new HashMap<>();
            raisedSquares = new HashSet<>();
        }

        public Builder addTunnel(Point loc) {
            tunnels.add(loc);
            return this;
        }

        public Builder addRaisedSquare(Point loc) {
            raisedSquares.add(loc);
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
            Map<Point, Square> board = new HashMap<>();
            Point max = new Point(0, 0);
            boolean isVictory = true;
            for (Map.Entry<Point, Piece> entry : pieces.entrySet()) {
                if (entry.getValue() == Piece.RABBIT && !tunnels.contains(entry.getKey())) {
                    isVictory = false;
                    break;
                }
            }
            for (Point point : tunnels) {
                board.put(point, new Square(true, true, pieces.get(point)));
                pieces.remove(point);
                updateMax(max, point);
            }
            for (Point point : raisedSquares) {
                board.put(point, new Square(false, true, pieces.get(point)));
                pieces.remove(point);
                updateMax(max, point);
            }
            for (Map.Entry<Point, Piece> entry : pieces.entrySet()) {
                board.put(entry.getKey(), new Square(false, false, entry.getValue()));
                updateMax(max, entry.getKey());
            }
            return new Board(board, max, isVictory);
        }
    }

    private final Map<Point, Square> board;
    private final Point max;
    private final boolean isVictory;

    private Board(Map<Point, Square> board, Point max, boolean isVictory) {
        this.board = Collections.unmodifiableMap(board);
        this.max = max;
        this.isVictory = isVictory;
    }

    public Square getSquare(Point loc) {
        return board.get(loc);
    }

    public boolean hasSquare(Point loc) {
        return board.containsKey(loc);
    }

    private boolean isVictory() {
        return isVictory;
    }

    private Map<Point, Square> getBoard() {
        return board;
    }

    private Point getMax() {
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
                stringBuilder.append(String.format("%1$" + 17 + "s", hasSquare(point) ? getSquare(point).toString() : "Empty"));
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
        return getBoard().equals(board.getBoard());
    }

    @Override
    public Iterator<Map.Entry<Point, Square>> iterator() {
        return board.entrySet().iterator();
    }
}
