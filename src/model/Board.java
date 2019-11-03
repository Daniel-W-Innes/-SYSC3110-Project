package model;

import helpers.Observer;
import helpers.Piece;
import helpers.Square;
import view.View;

import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A class representing the state of the Board.
 * The board is the only class observed by a view.
 * @author frank liu, daniel innes
 */
public class Board implements Model {

    /**
     * The Board map containing all squares.
     */
    private final Map<Point, Square> board;
    private final Point max;
    private View view;

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
    }

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

    public Map<Point, Square> getBoard() {
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
                stringBuilder.append(hasSquare(point) ? getSquare(point).toString() : "__");
            }
            stringBuilder.append('|');
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }


    /**
     * Check if all rabbit are in holes.
     *
     * @return If the board is victory
     */
    //Check if there are any rabbit
    public boolean isVictory() {
        return board.values().stream()
                .filter(Square::hasPiece)
                .filter(square -> square.getPiece().equals(null))
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

    //TODO: This shouldn't even exist???
    @Override
    public void addView(View view) {
        this.view = view;

        this.view.sendBoardTerrain(this);
    }

    @Override
    public void removeView(View view) {
        this.view = null;
    }

    /**
     * A class used to build a board using the Builder design pattern.
     */
    public static class Builder {

        /** A set of Points that contain a hole */
        private final Set<Point> hole;

        /** A set of Points that contain a raised square */
        private final Set<Point> raisedSquares;

        /** A set of Points that contain a piece */
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

        /**
         * Adds a hole at the given location
         * @param loc a location (Point)
         * @return this builder instance
         */
        public Builder addHole(Point loc) {
            hole.add(loc);
            return this;
        }

        /**
         * Adds a raised square at the given location
         * @param loc a location (Point)
         * @return this builder instance
         */
        public Builder addRaisedSquare(Point loc) {
            raisedSquares.add(loc);
            return this;
        }

        /**
         * Adds a Piece at the given location
         * @param loc a location (Point)
         * @param piece the piece to add
         * @return this builder instance
         */
        public Builder addPieces(Point loc, Piece piece) {
            pieces.put(loc, piece);
            return this;
        }

        /**
         * Ensures that the max.x is less then point.x and max.y is less then point.y and changes max to according to make the conditions true.
         * This is called to determine the larges x, y coordinate of the board
         * @param max the max point of the board
         * @param point the point to encompass in max
         */
        private void updateMax(Point max, Point point) {
            if (point.x > max.x) {
                max.x = point.x;
            }
            if (point.y > max.y) {
                max.y = point.y;
            }
        }

        /**
         * Check if the foxes are positioned correctly then build a Board from the Builder.
         *
         * @return The game board.
         */
        /*public Board build() {
            Map<Point, Square> board = new HashMap<>();
            Point max = new Point(0, 0);
            //Check if the foxes positions
            Set<Point> badFoxLocs = new HashSet<>();
            //Check for lone foxes
            pieces.entrySet().stream()
                    .filter(entry -> {
                        switch (entry.getValue()) {
                            case FOX_MINUS_X -> {
                                return !pieces.containsKey(new Point(entry.getKey().x + 1, entry.getKey().y));
                            }
                            case FOX_MINUS_Y -> {
                                return !pieces.containsKey(new Point(entry.getKey().x, entry.getKey().y + 1));
                            }
                            case FOX_PLUS_X -> {
                                return !pieces.containsKey(new Point(entry.getKey().x - 1, entry.getKey().y));
                            }
                            case FOX_PLUS_Y -> {
                                return !pieces.containsKey(new Point(entry.getKey().x, entry.getKey().y - 1));
                            }
                            default -> {
                                return false;
                            }
                        }
                    }).forEach(entry -> badFoxLocs.add(entry.getKey()));
            //Check for Foxes on raised squares
            pieces.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(Piece.FOX_MINUS_X) ||
                            entry.getValue().equals(Piece.FOX_MINUS_Y) ||
                            entry.getValue().equals(Piece.FOX_PLUS_X) ||
                            entry.getValue().equals(Piece.FOX_PLUS_Y))
                    .filter(entry -> hole.contains(entry.getKey()) ||
                            raisedSquares.contains(entry.getKey()))
                    .forEach(entry -> {
                        badFoxLocs.add(entry.getKey());
                        switch (entry.getValue()) {
                            case FOX_MINUS_X -> badFoxLocs.add(new Point(entry.getKey().x + 1, entry.getKey().y));
                            case FOX_MINUS_Y -> badFoxLocs.add(new Point(entry.getKey().x, entry.getKey().y + 1));
                            case FOX_PLUS_X -> badFoxLocs.add(new Point(entry.getKey().x - 1, entry.getKey().y));
                            case FOX_PLUS_Y -> badFoxLocs.add(new Point(entry.getKey().x, entry.getKey().y - 1));
                        }
                    });
            //Remove foxes that are not positioned correctly
            badFoxLocs.forEach(pieces::remove);
            //Add holes to the map
            for (Point point : hole) {
                board.put(point, new Square(true, true, pieces.get(point)));
                pieces.remove(point);
                updateMax(max, point);
            }
            //Add raised squares to the map
            for (Point point : raisedSquares) {
                board.put(point, new Square(false, true, pieces.get(point)));
                pieces.remove(point);
                updateMax(max, point);
            }
            //Add pieces to the map
            for (Map.Entry<Point, Piece> entry : pieces.entrySet()) {
                board.put(entry.getKey(), new Square(false, false, entry.getValue()));
                updateMax(max, entry.getKey());
            }
            return new Board(board, new Point(max));
        } */
    }
}
