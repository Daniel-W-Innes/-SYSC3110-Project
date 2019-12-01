package model;

import helpers.*;
import view.View;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A class representing the state of the Board.
 * The board is the only class observed by a view.
 */
public class Board implements Model {

    public final Point maxBoardSize;
    private final Map<Point, Piece> pieces;

    /**
     * The Board map containing all squares.
     */
    private final Map<Point, Square> terrain;
    private View view;

    public Board() {
        terrain = new HashMap<>();
        pieces = new HashMap<>();
        maxBoardSize = new Point(5, 5);
    }

    public Board(String fileName) throws IOException {
        this(Proto.Board.parseFrom(new FileInputStream(fileName)));
    }

    public Board(Proto.Board board) {
        pieces = new HashMap<>();
        maxBoardSize = new Point(board.getMaxBoardSize());
        terrain = Square.fromListOfProtos(board.getSquareList());
        pieces.putAll(Mushroom.fromListOfProtos(board.getMushroomList()));
        pieces.putAll(Fox.fromListOfProtos(board.getFoxList()));
        pieces.putAll(Rabbit.fromListOfProtos(board.getRabbitList()));
    }

    /**
     * Copy constructor. Creates an identical copy of the board passed in.
     *
     * @param board the board that should be copied.
     */

    public Board(Board board) {
        maxBoardSize = board.maxBoardSize;

        // The terrain never changes so it never needs to be copied. Same for the viewer.
        // If a copy of a board does not need to update the GUI, then when calling movePiece() on the game,
        // it can specify to not change the GUI

        terrain = board.terrain;
        view = board.view;

        // A separate copy of the pieces are needed as otherwise, a copy of a piece in one board
        // would affect the pieces in all of the other boards
        pieces = new HashMap<>();

        for (Map.Entry<Point, Piece> piece : board.pieces.entrySet()) {
            pieces.put(new Point(piece.getKey()), piece.getValue().clonePiece());
        }
    }

    /**
     * Checks to make sure that the passed in location is within the size of the board.
     *
     * @param location The location to check if it is within the board space
     * @throws IllegalArgumentException If the point is NOT within the board space
     */

    private void checkValidLocation(Point location) {
        if (0 > location.x || 0 > location.y) {
            throw new IllegalArgumentException("Invalid location for piece specified. Passed in: " + location);
        }
        // Index for points start at 0, meaning that even though the max board length is 5, any value above 4 leads
        // to an invalid index. Hence the minus 1 in the if statement.
        if (location.x > (maxBoardSize.x - 1) || location.y > (maxBoardSize.y - 1)) {
            throw new IllegalArgumentException("Invalid location for piece specified. Passed in: " + location + ".\nMax Board length = " + maxBoardSize);
        }
    }

    /**
     * Add a square to board at the given point.
     *
     * @param location The location on the board to add to
     * @param square   The square to add
     * @throws IllegalArgumentException If trying to add a new square to a location with an existing square
     */

    public void addSquare(Point location, Square square) {
        checkValidLocation(location);
        // Once a terrain square has been placed, it is considered an error to try to replace it with something else.
        if (terrain.containsKey(location)) {
            String errorMessage = "Trying to overwrite a Terrain square at: " + location;
            errorMessage += "Original square: " + terrain.get(location);
            errorMessage += "Attempting to replace with: " + square;
            throw new IllegalArgumentException(errorMessage);
        }

        terrain.put(location, square);
    }

    /**
     * Determines if there is a piece at the given location.
     *
     * @param location the point to check for a piece
     * @return true if there is a piece at that location
     */

    public boolean hasPiece(Point location) {
        return pieces.containsKey(location);
    }

    /**
     * Get if the board has a square at the given point.
     *
     * @param location The location on the square to check
     * @return If the board has the square
     */
    public boolean hasSquare(Point location) {
        return terrain.containsKey(location);
    }

    /**
     * Get all of the pieces on this board.
     *
     * @return A map of pieces on this board, with their respective locations
     */

    public Map<Point, Piece> getPieces() {
        return pieces;
    }

    /**
     * Get the Board map containing all squares.
     *
     * @return The Board map
     */
    public Map<Point, Square> getTerrain() {
        return terrain;
    }

    /**
     * Adds a game piece to the board at the given point.
     *
     * @param piece    The piece to add
     * @throws IllegalArgumentException If trying to add a new piece to a location with an existing piece
     */

    public void addPiece(Piece piece) {
        for (Point point : piece.boardSpotsUsed()) {
            checkValidLocation(point);
            // Once a piece has been placed, it is considered an error to try to replace it with something else.
            if (pieces.containsKey(point)) {
                String errorMessage = "Trying to overwrite a piece at: " + point;
                errorMessage += "Original piece: " + pieces.get(point);
                errorMessage += "Attempting to replace with: " + piece;
                throw new IllegalArgumentException(errorMessage);
            }
        /*
            If the piece being added is a fox, then it takes up two points. Both points must refer to the same
            object. Hence there is a need to loop over all of the board spots of the piece to ensure that all of
            the piece locations refer to the same object.
         */
            if (piece instanceof Fox) {
                if (terrain.containsKey(point)) {
                    throw new IllegalArgumentException("Attempting to add fox to an invalid location!");
                }
            }
            pieces.put(point, piece);
        }
    }

    /**
     * Move the given piece to the new location
     *
     * @param piece       The piece to move
     * @param newLocation The new location to move the piece to
     * @param applyChangesView If the board should notify the view
     */

    public void movePiece(Piece piece, Point newLocation, boolean applyChangesView) {
        piece.boardSpotsUsed().forEach(pieces::remove);
        if (applyChangesView) {
            piece.boardSpotsUsed().forEach(view::removePiece);
        }
        piece.updateBoardSpotUsed(newLocation);
        piece.boardSpotsUsed().forEach(point -> pieces.put(point, piece));
        if (applyChangesView) {
            piece.boardSpotsUsed().forEach(point -> view.addPiece(point, piece));
        }
        if (isVictory() && applyChangesView) {
            view.notifyWin();
        }
    }

    /**
     * Remove any piece at the given point from the board- if the piece exists.
     *
     * @param point the point at which any piece, if present, should be removed
     */

    public void removePiece(Point point, boolean applyChangesView) {
        // Since a piece could be a fox, which takes up two spaces, but is represented by one point,
        // it is necessary to loop over all of the board spots of the piece
        if (pieces.containsKey(point)) {
            if (applyChangesView) {
                pieces.get(point).boardSpotsUsed().forEach(view::removePiece);
            }
            Set<Point> boardSpotsUsed = pieces.get(point).boardSpotsUsed();
            for (Point boardSport : boardSpotsUsed) {
                pieces.remove(boardSport);
            }
        }
    }

    /**
     * Get a square at the given point from the board.
     * Note: This will return null if the board does not have a square at the point.
     *
     * @param loc The location on the square to get
     * @return The square, nullable
     */

    public Square getSquare(Point loc) {
        return terrain.get(loc);
    }


    public Proto.Board toProto() {
        return Proto.Board.newBuilder()
                .setMaxBoardSize(maxBoardSize.toProto())
                .addAllSquare(terrain.entrySet().stream()
                        .map(entry -> entry.getValue().toProto(entry.getKey()))
                        .collect(Collectors.toUnmodifiableSet()))
                .addAllFox(pieces.values().stream()
                        .filter(piece -> piece instanceof Fox)
                        .map(piece -> (Fox) piece)
                        .map(Fox::toProto)
                        .collect(Collectors.toUnmodifiableSet()))
                .addAllMushroom(pieces.values().stream()
                        .filter(piece -> piece instanceof Mushroom)
                        .map(piece -> (Mushroom) piece)
                        .map(Mushroom::toProto)
                        .collect(Collectors.toUnmodifiableSet()))
                .addAllRabbit(pieces.values().stream()
                        .filter(piece -> piece instanceof Rabbit)
                        .map(piece -> (Rabbit) piece)
                        .map(Rabbit::toProto)
                        .collect(Collectors.toUnmodifiableSet()))
                .build();
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
        for (int x = 0; x <= maxBoardSize.x; x++) {
            for (int y = 0; y <= maxBoardSize.y; y++) {
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

    private Piece getPiece(Point point) {
        return pieces.get(point);
    }

    /**
     * Check if all rabbit are in holes.
     *
     * @return If the board is victory
     */
    public boolean isVictory() {
        // Since a board could be checked for a winning state even if there are no rabbits,
        // as the board can now be created incrementally, it is necessary to ensure that a winning
        // notification is sent only if there are any rabbits, along with if they are in holes.
        int rabbitCount = 0;

        for (Map.Entry<Point, Piece> entry : pieces.entrySet()) {
            //Return false if a rabbit is not in a hole
            if (Rabbit.class == entry.getValue().getClass()) {
                rabbitCount += 1;
                if ((null == terrain.get(entry.getKey())) || !terrain.get(entry.getKey()).isHole()) {
                    return false;
                }
            }
        }

        return rabbitCount != 0;
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
        if (null == obj || obj.getClass() != getClass()) {
            return false;
        }
        Board board = (Board) obj;
        return pieces.equals(board.pieces);
    }

    /**
     * Get the hashcode for the board. This hashcode is the hashcode of the board map.
     *
     * @return The hashcode
     */
    @Override
    public int hashCode() {
        return terrain.hashCode() ^ (new HashSet<>(pieces.values())).hashCode();
    }

    /**
     * Set the view reference so that the board can notify the view of changes.
     *
     * @param view the view reference
     */

    @Override
    public void setView(View view) {
        this.view = view;
    }

    public void save(String fileName) throws IOException {
        toProto().writeTo(new FileOutputStream(fileName));
    }
}
