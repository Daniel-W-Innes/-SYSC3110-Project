package helpers;

import model.Board;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static controller.Game.resourcesFolder;

public class Mushroom implements Piece {

    private Point boardSpot;
    private static final ImageIcon icon = new ImageIcon(resourcesFolder + File.separator + "pieces" + File.separator + "Mushroom.png");

    public Mushroom(Point boardSpot) {
        this.boardSpot = boardSpot;
    }

    @Override
    public void updateBoardSpotUsed(Point newLocation) {
        boardSpot = new Point(newLocation);
    }

    @Override
    public Set<Point> boardSpotsUsed() {
        return Set.of(boardSpot);
    }

    @Override
    public List<Move> getMoves(Board board, Point clickedPoint) {
        return new ArrayList<>();
    }

    @Override
    public ImageIcon getImageIcon(Point location) {
        return icon;
    }

}
