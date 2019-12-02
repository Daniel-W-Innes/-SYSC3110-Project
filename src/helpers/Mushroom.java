package helpers;

import model.Board;

import javax.swing.*;
import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static controller.Game.resourcesFolder;

/**
 * Class that represents a mushroom piece in the game.
 */

public class Mushroom implements Piece {

    static final String imageIconLocation = resourcesFolder + File.separator + "pieces" + File.separator + "Mushroom.png";
    private static final ImageIcon icon = new ImageIcon(imageIconLocation);
    private Point boardSpot;

    /**
     * Constructor that initializes the mushroom to the given location
     *
     * @param boardSpot the initial location to place the mushroom
     */

    public Mushroom(Point boardSpot) {
        this.boardSpot = boardSpot;
    }

    private Mushroom(Proto.Mushroom mushroom) {
        boardSpot = new Point(mushroom.getBoardSpot());
    }

    public static Map<Point, Mushroom> fromListOfProtos(Collection<Proto.Mushroom> mushrooms) {
        return mushrooms.stream()
                .collect(Collectors.toMap(mushroom -> new Point(mushroom.getBoardSpot()), Mushroom::new));
    }

    public Proto.Mushroom toProto() {
        return Proto.Mushroom.newBuilder()
                .setBoardSpot(boardSpot.toProto())
                .build();
    }

    /**
     * Returns the String representation of the mushroom.
     *
     * @return String showing a textual representation of the mushroom
     */

    @Override
    public String toString() {
        return "M";
    }

    /**
     * Determines if two mushroom objects are either the same object or are logically equivalent.
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
        Mushroom mushroom = (Mushroom) obj;
        return boardSpot.equals(mushroom.boardSpot);
    }

    /**
     * The hash code for the mushroom object.
     *
     * @return the hashcode for this mushroom object.
     */

    @Override
    public int hashCode() {
        return boardSpot.hashCode() ^ toString().hashCode();
    }

    /**
     * Get a new equivalent copy of this mushroom. The new object is a separate object.
     *
     * @return a copy of this mushroom object
     */

    public Piece clonePiece() {
        return new Mushroom(boardSpot);
    }

    /**
     * Update the internal variables holding the location of the mushroom
     *
     * @param newLocation the new location of the head of the mushroom
     */

    @Override
    public void updateBoardSpotUsed(Point newLocation) {
        boardSpot = new Point(newLocation);
    }

    @Override
    public Set<Point> getAvailableSpots(Board board, Set<Point> points) {
        return points.stream()
                .filter(point -> !board.getPieces().containsKey(point))
                .filter(point -> !board.getTerrain().containsKey(point) || !board.getTerrain().get(point).isHole())
                .collect(Collectors.toSet());
    }

    /**
     * Get all of the board spots used by the mushroom piece.
     *
     * @return points taken up by the mushroom piece
     */

    @Override
    public Set<Point> boardSpotsUsed() {
        return Set.of(boardSpot);
    }

    /**
     * Find the possible moves the mushroom can take given the current state of the board.
     *
     * @param board        the model of the board
     * @param clickedPoint the point the user click in the BoardPanel
     * @return list of moves that the mushroom can take
     */

    @Override
    public Set<Point> getEndPoint(Board board, Point clickedPoint) {
        return new HashSet<>();
    }

    /**
     * Get the texture that should be drawn to represent the mushroom at a given point.
     *
     * @param location the location specifying where the mushroom texture needs to be drawn
     * @return ImageIcon that holds the texture needed to be drawn
     */

    @Override
    public ImageIcon getImageIcon(Point location) {
        return icon;
    }

    /**
     * Returns the icon that should be used by the LevelEditorButtons.
     * <p>
     * Note that this could be obtained from the getImageIcon function, but due to the usage of fox
     * such a function helps in readability and separation of duties.
     *
     * @return ImageIcon to be used for the LevelEditorButton
     */

    @Override
    public ImageIcon getLevelEditorIcon() {
        return icon;
    }
}
