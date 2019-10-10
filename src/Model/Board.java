package Model;

import Controller.Piece;
import Controller.twoLongPiece;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board {

    public static class Builder {
        private Set<Point> tunnels;
        private Set<Point> raisedSquares;
        private Map<Point, Piece> pieces;

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

        public Builder addPieces(Point loc, twoLongPiece piece, Direction direction) {
            piece.setDirection(direction);
            pieces.put(loc, piece);
            switch (direction) {
                case UP:
                    pieces.put(new Point(loc.x, loc.y - 1), piece);
                    break;
                case DOWN:
                    pieces.put(new Point(loc.x, loc.y + 1), piece);
                    break;
                case LEFT:
                    pieces.put(new Point(loc.x - 1, loc.y), piece);
                    break;
                case RIGHT:
                    pieces.put(new Point(loc.x + 1, loc.y), piece);
                    break;
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
            Map<Point, Square> board = new HashMap<>();
            Point max = new Point(0, 0);
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
            return new Board(board, max);
        }
    }

    private Map<Point, Square> board;
    private Point max;

    private Board(Map<Point, Square> board, Point max) {
        this.board = board;
        this.max = max;
    }

    public Square getSquare(Point loc) {
        return board.get(loc);
    }

    public boolean hasSquare(Point loc) {
        return board.containsKey(loc);
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
