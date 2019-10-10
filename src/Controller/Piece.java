package Controller;

import java.awt.*;

public abstract class Piece {
    abstract boolean move(Point oldLoc, Point newLoc);
}
