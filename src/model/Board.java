package model;

import view.Observer;

import java.awt.*;
import java.util.*;

public class Board implements Iterable<Map.Entry<Point, Square>>, Observable{

    public static class Builder {
        private final Set<Point> hole;
        private final Set<Point> raisedSquares;
        private final Map<Point, Piece> pieces;

        public Builder() {
            hole = new HashSet<>();
            pieces = new HashMap<>();
            raisedSquares = new HashSet<>();
        }

        public Builder addHole(Point loc) {
            hole.add(loc);
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

        private void updateMax(java.awt.Point max, Point point) {
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
            for (Point point : hole) {
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
            return new Board(board, new Point(max));
        }
    }

    private final Map<Point, Square> board;
    private final Point max;
    private Observer observer;

    private Board(Map<Point, Square> board, Point max) {
        this.board = new HashMap<>(board);
        this.max = max;
    }

    @Override
    public void setObserver(Observer observer) {
        this.observer = observer;
    }

    @Override
    public void notifyObserver() {
        if (this.observer != null){
            this.observer.update(this);
        }
    }

    public Square getSquare(Point loc) {
        return board.get(loc);
    }

    public void addSquare(Point loc, Square square) {
        board.put(loc, square);
    }

    public boolean hasSquare(Point loc) {
        return board.containsKey(loc);
    }

    public void removeSquareIfEmpty(Point loc) {
        if (board.containsKey(loc) && !(board.get(loc).hasPiece()) || board.get(loc).isRaised() || board.get(loc).isHole()) {
            board.remove(loc);
        }
    }

    private Map<Point, Square> getBoard() {
        return board;
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
    public int hashCode() {
        return getBoard().hashCode();
    }

    @Override
    public Iterator<Map.Entry<Point, Square>> iterator() {
        return board.entrySet().iterator();
    }
}
