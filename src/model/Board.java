package model;

import helpers.Foxes;
import helpers.Piece;
import helpers.Rabbits;
import helpers.Square;
import view.View;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
// TODO: add a move method that checks for a victory and if there is one, notify the view of that victory
/**
 * A class representing the state of the Board.
 * The board is the only class observed by a view.
 * @author frank liu, daniel innes
 */
public class Board implements Model {

    /**
     * The Board map containing all squares.
     */
    private final Map<Point, Square> terrain;
    private final Map<Point, Piece> pieces;
    public static final Point maxBoardLength = new Point(5, 5);
    private View view;

    public Board() {
        terrain = new HashMap<>();
        pieces = new HashMap<>();
    }

    /**
     * Add a square to board at the given point.
     *
     * @param location The location on the board to add to
     * @param square The square to add
     *
     * @throws IllegalArgumentException If trying to add a new square to a location with an existing square
     */

    public void addSquare(Point location, Square square)
    {

        checkValidLocation(location);

        // Once a terrain square has been placed, it is considered an error to try to replace it with something else.
        if(terrain.containsKey(location))
        {
            String errorMessage = "Trying to overwrite a Terrain square at: " + location.toString();
            errorMessage += "Original square: " + terrain.get(location).toString();
            errorMessage += "Attempting to replace with: " + square.toString();

            throw new IllegalArgumentException(errorMessage);
        }

        terrain.put(location, square);
    }

    /**
     * Adds a game piece to the board at the given point.
     *
     * @param location The location on the board to add to
     * @param piece The piece to add
     *
     * @throws IllegalArgumentException If trying to add a new piece to a location with an existing piece
     */

    public void addPiece(Point location, Piece piece)
    {
        checkValidLocation(location);

        // Once a piece has been placed, it is considered an error to try to replace it with something else.
        if(pieces.containsKey(location))
        {
            String errorMessage = "Trying to overwrite a Terrain square at: " + location.toString();
            errorMessage += "Original square: " + pieces.get(location).toString();
            errorMessage += "Attempting to replace with: " + piece.toString();

            throw new IllegalArgumentException(errorMessage);
        }

        /*
            If the piece being added is a fox, then it takes up two points. Both points must refer to the same
            object. Hence there is a need to loop over all of the board spots of the piece to ensure that all of
            the piece locations refer to the same object.
         */

        for(Point occupiedPoint : piece.boardSpotsUsed()) {
            if(pieces.containsKey(occupiedPoint) || terrain.containsKey(occupiedPoint)) {
                throw new IllegalArgumentException("Attempting to add fox to an invalid location!");
            }

            pieces.put(occupiedPoint, piece);
            //this.view.addPiece(occupiedPoint, piece);
        }
    }

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

    public void movePiece(Piece piece, Point newLocation) {

        /* When moving a piece, the fox has to be dealt with separately in the following way:
         *  > When removing the fox, the head is removed. This is done through the getHeadLocation() method
         *  > After removing the head, and knowing the direction of the fox, the tail location can be found and removed
         *  > This process is repeated to remove the pieces from the view
         *
         *  > When adding a fox, the opposite is done. The head is added to the new location, and knowing the direction
         *   of the fox the respective location of the tail can be found. Both of these new locations are added to the
         *   piece map in addition to the view.
         *
         *  Remember that the tail is always on one side of the head!
         */

        if(piece instanceof Foxes) {
            Foxes removedPiece = (Foxes)piece;

            if(removedPiece.getDirection() == Foxes.Direction.X_AXIS){
                pieces.remove(removedPiece.getHeadLocation()); // Remove fox head
                pieces.remove(new Point(removedPiece.getHeadLocation().x - 1, removedPiece.getHeadLocation().y)); // Remove fox tail
                view.removePiece(removedPiece.getHeadLocation()); // Remove fox head from view
                view.removePiece(new Point(removedPiece.getHeadLocation().x - 1, removedPiece.getHeadLocation().y)); // Remove fox tail from view

                // Since the piece has moved, its internal representation has to be updated as well to match the new location
                // of it stored in the board
                piece.updateBoardSpotUsed(newLocation);

                pieces.put(newLocation, removedPiece); // Add new fox head
                pieces.put(new Point(newLocation.x - 1, newLocation.y), removedPiece); // Add new fox tail
                view.addPiece(newLocation, removedPiece); // Add new fox head to view
                view.addPiece(new Point(newLocation.x - 1, newLocation.y), removedPiece); // Add new fox tail to view
            }
            else{
                pieces.remove(removedPiece.getHeadLocation());
                pieces.remove(new Point(removedPiece.getHeadLocation().x, removedPiece.getHeadLocation().y - 1));
                view.removePiece(removedPiece.getHeadLocation());
                view.removePiece(new Point(removedPiece.getHeadLocation().x, removedPiece.getHeadLocation().y - 1));

                // Since the piece has moved, its internal representation has to be updated as well to match the new location
                // of it stored in the board
                piece.updateBoardSpotUsed(newLocation);

                pieces.put(newLocation, removedPiece);
                pieces.put(new Point(newLocation.x, newLocation.y - 1), removedPiece);
                view.addPiece(newLocation, removedPiece);
                view.addPiece(new Point(newLocation.x, newLocation.y - 1), removedPiece);
            }
        }
        else
        {
            // Since every piece that is not a fox takes only one board square, the collection returned by boardSpotsUsed()
            // can only have one result, which corresponds to the location of the piece
            for(Point point : piece.boardSpotsUsed()) {
                pieces.remove(point);
                view.removePiece(point);
            }
            // Since the piece has moved, its internal representation has to be updated as well to match the new location
            // of it stored in the board
            piece.updateBoardSpotUsed(newLocation);

            pieces.put(newLocation, piece);
            view.addPiece(newLocation, piece);
        }
        if (isVictory()) {
            view.notifyWin();
        }
    }

    /**
     *  Checks to make sure that the passed in location is within the size of the board.
     *
     * @param location The location to check if it is within the board space
     *
     * @throws IllegalArgumentException If the point is NOT within the board space
     */

    private void checkValidLocation(Point location)
    {
        if(location.x < 0 || location.y < 0)
        {
            throw new IllegalArgumentException("Invalid location for piece specified. Passed in: " + location.toString());
        }

        // Index for points start at 0, meaning that even though the max board length is 5, any value above 4 leads
        // to an invalid index. Hence the minus 1 in the if statement.
        if (location.x > (maxBoardLength.x - 1) || location.y > (maxBoardLength.y - 1))
        {
            throw new IllegalArgumentException("Invalid location for piece specified. Passed in: " + location.toString() + ".\nMax Board length = 5");
        }
    }

    /**
     * Get a square at the given point from the board.
     * Note: This will return null if the board does not have a square at the point.
     *
     * @param loc The location on the square to get
     * @return The square, nullable
     */

    private Square getSquare(Point loc) {
        return terrain.get(loc);
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
        for (int x = 0; x <= maxBoardLength.x; x++) {
            for (int y = 0; y <= maxBoardLength.y; y++) {
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
    private boolean isVictory() {
        for(Map.Entry<Point, Piece> entry : this.pieces.entrySet()) {
            //Return false if a rabbit is not in a hole
            if(entry.getValue().getClass() == Rabbits.class
                    && ((this.terrain.get(entry.getKey()) == null) || !this.terrain.get(entry.getKey()).isHole())){
                return false;
            }
        }
        return true;

//        return terrain.values().stream()
//                .filter(Square::hasPiece)
//                .filter(square -> square.getPiece().equals(Piece.RABBIT))
//                .allMatch(Square::isHole);
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
        return getTerrain().equals(board.getTerrain());
    }

    /**
     * Get the hashcode for the board. This hashcode is the hashcode of the board map.
     *
     * @return The hashcode
     */

    @Override
    public int hashCode() {
        return getTerrain().hashCode();
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }
}
