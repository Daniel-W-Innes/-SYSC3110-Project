package helpers;

import model.Board;
import protos.RabbitOuterClass;

import javax.swing.*;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static controller.Game.resourcesFolder;

/**
 * Class that represents a rabbit piece in the game.
 */

public class Rabbit implements Piece {

    static final String imageIconLocation = resourcesFolder + File.separator + "pieces" + File.separator + "Rabbit_white.png";
    private static final ImageIcon icon = new ImageIcon(imageIconLocation);
    private Point boardSpot;

    /**
     * Constructor that initializes the rabbit to the given location
     *
     * @param boardSpot the initial location to place the rabbit
     */

    public Rabbit(Point boardSpot) {
        this.boardSpot = boardSpot;
    }

    private Rabbit(RabbitOuterClass.Rabbit rabbit) {
        boardSpot = new Point(rabbit.getBoardSpot());
    }

    /**
     * Internal function to help find possible moves for the rabbit for a given direction.
     *
     * @param board     the model of the board
     * @param start     the point at which to start looking for possible moves
     * @param direction the direction to check for possible moves
     * @return the list of possible moves for the given direction starting at the passed in point
     */

    private static List<Move> getMoveDirection(Board board, Point start, Point direction) {
        /*
             To find possible moves for the rabbits, search the given direction for obstacles. While
             there are obstacles, continue searching the next square in the given direction. Once no more
             obstacles are found, then the end point for a move has been found.
         */
        Point startingPointCopy = new Point(start);
        List<Move> possibleMoves = new ArrayList<>();
        boolean objectToJump = true;
        while (objectToJump) {
            startingPointCopy = startingPointCopy.move(direction);
            objectToJump = board.hasPiece(startingPointCopy);
        }
        // If there are no pieces in the immediate square adjacent to the rabbit in the given rabbit, there are no moves
        if (start.equals(new Point(startingPointCopy.x - direction.x, startingPointCopy.y - direction.y))) {
            return possibleMoves;
        }
        // Make sure that the possible move is within valid coordinates of the game. The '-1' seen is because
        // the coordinates star at 0, not 1.
        if (0 <= startingPointCopy.x && startingPointCopy.x <= board.maxBoardSize.x - 1 && 0 <= startingPointCopy.y && startingPointCopy.y <= board.maxBoardSize.y - 1) {
            possibleMoves.add(new Move(new Point(start), new Point(startingPointCopy)));
            return possibleMoves;
        }
        return possibleMoves;
    }

    public static Map<Point, Rabbit> fromListOfProtos(Collection<RabbitOuterClass.Rabbit> mushrooms) {
        return mushrooms.stream()
                .collect(Collectors.toMap(rabbit -> new Point(rabbit.getBoardSpot()), Rabbit::new));
    }

    public RabbitOuterClass.Rabbit toProto() {
        return RabbitOuterClass.Rabbit.newBuilder()
                .setBoardSpot(boardSpot.toProto())
                .build();
    }

    /**
     * Returns the String representation of the rabbit.
     *
     * @return String showing a textual representation of the rabbit
     */

    @Override
    public String toString() {
        return "R";
    }

    /**
     * Determines if two rabbit objects are either the same object or are logically equivalent.
     *
     * @param obj the object to compare
     * @return true if the two are the same
     */


    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (null == obj || obj.getClass() != getClass()) {
            return false;
        }
        Rabbit rabbit = (Rabbit) obj;
        return boardSpot.equals(rabbit.boardSpot);
    }

    /**
     * The hash code for the rabbit object.
     *
     * @return the hashcode for this rabbit object.
     */

    @Override
    public int hashCode() {
        return boardSpot.hashCode() ^ toString().hashCode();
    }

    /**
     * Get a new equivalent copy of this rabbit. The new object is a separate object.
     *
     * @return a copy of this rabbit object
     */

    @Override
    public Piece clonePiece() {
        return new Rabbit(new Point(boardSpot));
    }

    /**
     * Update the internal variables holding the location of the rabbit
     *
     * @param newLocation the new location of the head of the rabbit
     */


    @Override
    public void updateBoardSpotUsed(Point newLocation) {
        boardSpot = new Point(newLocation);
    }

    /**
     * Find the possible moves the rabbit can take given the current state of the board.
     *
     * @param board        the model of the board
     * @param clickedPoint the point the user click in the BoardPanel
     * @return list of moves that the rabbit can take
     */

    @Override
    public List<Move> getMoves(Board board, Point clickedPoint) {
        List<Move> possibleMoves = new ArrayList<>();
        // Possible moves could exist in any of the four directions depending on the obstacles of the board
        // Thus all directions have to be checked.
        possibleMoves.addAll(getMoveDirection(board, boardSpot, new Point(1, 0)));
        possibleMoves.addAll(getMoveDirection(board, boardSpot, new Point(-1, 0)));
        possibleMoves.addAll(getMoveDirection(board, boardSpot, new Point(0, 1)));
        possibleMoves.addAll(getMoveDirection(board, boardSpot, new Point(0, -1)));

        return possibleMoves;
    }

    /**
     * Get all of the board spots used by the rabbit piece.
     *
     * @return points taken up by the rabbit piece
     */

    @Override
    public Set<Point> boardSpotsUsed() {
        return Set.of(boardSpot);
    }


    /**
     * Get the texture that should be drawn to represent the rabbit at a given point.
     *
     * @param location the location specifying where the rabbit texture needs to be drawn
     * @return ImageIcon that holds the texture needed to be drawn
     */

    @Override
    public ImageIcon getImageIcon(Point location) {
        return icon;
    }
}
