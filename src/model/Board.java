package model;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board {

    public static class Builder {
        private final Set<Point> tunnels;
        private final Set<Point> raisedSquares;
        private final Map<Point, Piece> pieces;

        public Builder() {
            tunnels = new HashSet<>();
            pieces = new HashMap<>();
            raisedSquares = new HashSet<>();
        }

        public Builder addTunnel(Point Loc) {
            tunnels.add(Loc);
            return this;
        }

        public Builder addRaisedSquare(Point Loc) {
            raisedSquares.add(Loc);
            return this;
        }

        public Builder addPieces(Point Loc, Piece piece) {
            pieces.put(Loc, piece);
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
        this.board = board;
        this.max = max;
        this.isVictory = isVictory;
    }

    public Square getSquare(Point loc) {
        return board.get(loc);
    }

    public boolean hasSquare(Point loc) {
        return board.containsKey(loc);
    }

    public boolean isVictory() {
        return isVictory;
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
}
