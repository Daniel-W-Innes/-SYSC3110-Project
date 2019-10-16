package model;

import view.Observer;

import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 */
public class Board implements Observable {

    /**
     * The Board map containing all squares.
     */
    private final Map<Point, Square> board;

    /**
     * Initialize from the builder.
     *
     * @param board The board map
     * @param max   The Max Point located on the board
     */
    private Board(Map<Point, Square> board, Point max) {
        this.board = new HashMap<>(board);
        this.max = max;
    }

    private final Point max;
    private Observer observer;

    /**
     * Initialize a new with to a defensive copy of the given board.
     *
     * @param board The board to copy
     */

    public Board(Board board) {
        this.board = board.getBoard().entrySet().stream()
                .map(entry -> Map.entry(entry.getKey(), new Square(entry.getValue().isHole(), entry.getValue().isRaised(), entry.getValue().getPiece())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        this.max = board.getMax();
        this.observer = board.getObserver();
    }

    /**
     * Get a square at the given point from the board.
     * Note: This will return null if the board does not have a square at the point.
     *
     * @param loc The location on the square to get
     * @return The square, nullable
     */

    public Square getSquare(Point loc) {
        return board.get(loc);
    }

    @Override
    public void setObserver(Observer observer) {
        this.observer = observer;
    }

    @Override
    public void notifyObserver() {
        if (this.observer != null){
            this.observer.update(new EventObject(this));
        }
    }

    @Override
    public Observer getObserver() {
        return observer;
    }

    /**
     * Add a square to board at the given point.
     *
     * @param loc    The location on the square to add
     * @param square Ths square to add
     */

    public void addSquare(Point loc, Square square) {
        board.put(loc, square);
        if (loc.x > max.x) {
            max.x = loc.x;
        }
        if (loc.y > max.y) {
            max.y = loc.y;
        }
    }

    /**
     * Get if the board has a square at the given point.
     *
     * @param loc The location on the square to check
     * @return If the board has the square
     */

    public boolean hasSquare(Point loc) {
        return board.containsKey(loc);
    }

    /**
     * Remove the square at a point if it is empty.
     *
     * @param loc The location on the square to check
     */

    public void removeSquareIfEmpty(Point loc) {
        if (board.containsKey(loc) && !(board.get(loc).hasPiece()) || board.get(loc).isRaised() || board.get(loc).isHole()) {
            board.remove(loc);
        }
    }

    /**
     * Get the Board map containing all squares.
     *
     * @return The Board map
     */

    private Map<Point, Square> getBoard() {
        return board;
    }

    /**
     * Get the Max Point located on the board.
     * The max x point is {@code getMax().x}, and the max y point is {@code getMax().y}.
     *
     * @return Max Point located on the board
     */
    public Point getMax() {
        return max;
    }

    /**
     * Get a string representation of the board. The String is formatted as squares separated by a "|".
     *
     * @return The representative string
     */
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


    public boolean isVictory() {
        return board.values().parallelStream()
                .filter(Square::hasPiece)
                .filter(square -> square.getPiece().equals(Piece.RABBIT))
                .allMatch(Square::isHole);
    }

    /**
     * Two board are equal when they both the the board map and therefor the squares. The {@code max} and {@code observer} a fields are noe checked.
     *
     * @param obj The object to test against
     * @return If obj is the same as this
     */

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

    /**
     * Get the hashcode for the board. This hashcode is the hashcode of the board map.
     *
     * @return The hashcode
     */

    @Override
    public int hashCode() {
        return getBoard().hashCode();
    }

    public static class Builder {
        private final Set<Point> hole;
        private final Set<Point> raisedSquares;
        private final Map<Point, Piece> pieces;

        public Builder(boolean useDefaultMap) {
            if (useDefaultMap) {
                hole = Set.of(new Point(0, 0), new Point(4, 4), new Point(0, 4), new Point(4, 0), new Point(2, 2));
                raisedSquares = Set.of(new Point(0, 2), new Point(2, 0), new Point(2, 4), new Point(4, 2));
                pieces = new HashMap<>();
            } else {
                hole = new HashSet<>();
                pieces = new HashMap<>();
                raisedSquares = new HashSet<>();
            }
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
}
