package helpers;

import model.Board;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Set;

/**
 * Interface that every game piece must implement.
 */

public interface Piece {

    Piece clonePiece();

    Set<Point> boardSpotsUsed();
    List<Move> getMoves(Board board, Point clickedPoint);

    ImageIcon getImageIcon(Point location);
    void updateBoardSpotUsed(Point newLocation);
}
