package helpers;

import model.Board;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Mushroom implements Piece {

    private Point boardSpot;
    private static final ImageIcon icon = new ImageIcon("./resources/Mushroom.jpg");

    public Mushroom(Point boardSpot) {
        this.boardSpot = boardSpot;
    }

    @Override
    public void updateBoardSpotUsed(Point newLocation) {
        boardSpot = new Point(newLocation);
    }

    @Override
    public Set<Point> boardSpotsUsed() {
        return Set.of(this.boardSpot);
    }

    @Override
    public List<Move> getMoves(Board board) {
        return new ArrayList<>();
    }

    @Override
    public ImageIcon getImageIcon(Point p) {
        return Mushroom.icon;
    }

}
