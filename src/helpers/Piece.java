package helpers;

import model.Board;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Set;

public interface Piece {

    Set<Point> boardSpotsUsed();
    List<Move> getMoves(Board board, Point clickedPoint);
    ImageIcon getImageIcon(Point p);
    void updateBoardSpotUsed(Point newLocation);
}
