package helpers;

import model.Board;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Mushroom implements Piece {

    private Point boardSpot;

    public Mushroom(Point boardSpot) {
        this.boardSpot = boardSpot;
    }

    @Override
    public void updateBoardSpotUsed(Point newLocation) {
        boardSpot = new Point(newLocation);
    }

    @Override
    public Set<Point> boardSpotsUsed() {
        return null;
    }

    @Override
    public List<Move> getMoves(Board board) {
        return new ArrayList<>();
    }

    @Override
    public ImageIcon getImageIcon() {
        return null;
    }

}
