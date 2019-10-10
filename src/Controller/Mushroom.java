package Controller;

import java.awt.*;

public class Mushroom extends Piece {
    @Override
    public boolean move(Point oldLoc, Point newLoc) {
        return false;
    }

    @Override
    public String toString() {
        return "Mushroom";
    }
}
