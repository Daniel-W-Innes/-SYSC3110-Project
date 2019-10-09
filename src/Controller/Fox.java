package Controller;

import java.awt.*;

public class Fox implements Piece {

    private final BoardManager boardManager;

    Fox(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    @Override
    public boolean move(Point oldLoc, Point newLoc) {
        return false;
    }

    @Override
    public String toString() {
        return "Fox";
    }
}
