package Controller;

import java.awt.*;

public class Rabbit implements Piece {
    private final BoardManager boardManager;

    Rabbit(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    @Override
    public boolean move(Point oldLoc, Point newLoc) {
        if (boardManager.getPiece(newLoc) == null) {
            boolean valid = true;
            if (!(oldLoc.x != newLoc.x && oldLoc.y != newLoc.y)) {
                if (oldLoc.x > newLoc.x) {
                    for (int i = newLoc.x; i < oldLoc.x; i++) {
                        if (boardManager.getPiece(new Point(i, newLoc.y)) == null) {
                            valid = false;
                        }
                    }
                } else if (oldLoc.y > newLoc.y) {
                    for (int i = newLoc.y; i < oldLoc.y; i++) {
                        if (boardManager.getPiece(new Point(newLoc.x, i)) == null) {
                            valid = false;
                        }
                    }
                } else if (oldLoc.x < newLoc.x) {
                    for (int i = oldLoc.x; i < newLoc.x; i++) {
                        if (boardManager.getPiece(new Point(i, newLoc.y)) == null) {
                            valid = false;
                        }
                    }
                } else if (oldLoc.y < newLoc.y) {
                    for (int i = oldLoc.y; i < newLoc.y; i++) {
                        if (boardManager.getPiece(new Point(newLoc.x, i)) == null) {
                            valid = false;
                        }
                    }
                }
                if (valid) {
                    boardManager.movePiece(oldLoc, newLoc);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Rabbit";
    }
}
