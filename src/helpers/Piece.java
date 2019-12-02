package helpers;

import model.Board;

import javax.swing.*;
import java.util.Set;

/**
 * Interface that every game piece must implement.
 */

public interface Piece {

    Piece clonePiece();

    Set<Point> boardSpotsUsed();

    Set<Point> getEndPoint(Board board, Point clickedPoint);

    ImageIcon getImageIcon(Point location);

    ImageIcon getLevelEditorIcon();

    void updateBoardSpotUsed(Point newLocation);

    Set<Point> getAvailableSpots(Board board, Set<Point> points);
}
