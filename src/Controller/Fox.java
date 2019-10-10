package Controller;

import Model.Direction;

import java.awt.*;

public class Fox extends twoLongPiece {
    private Direction direction;
    private final BoardManager boardManager;

    Fox(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    @Override
    public boolean move(Point oldLoc, Point newLoc) {
        return false; //TODO make foxes move
    }

    @Override
    public String toString() {
        return "Fox";
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
