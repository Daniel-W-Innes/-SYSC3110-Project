package Controller;

import Model.Board;
import Model.Direction;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class BoardManager {
    private Board board;
    private final Map<Integer, Board> levels;

    BoardManager() {
        levels = new HashMap<>();
        levels.put(20, new Board.Builder()
                .addTunnel(new Point(0, 0))
                .addTunnel(new Point(4, 4))
                .addTunnel(new Point(0, 4))
                .addTunnel(new Point(4, 0))
                .addTunnel(new Point(2, 2))

                .addRaisedSquare(new Point(0, 2))
                .addRaisedSquare(new Point(2, 0))
                .addRaisedSquare(new Point(2, 4))
                .addRaisedSquare(new Point(4, 2))

                .addPieces(new Point(1, 4), new Rabbit(this))
                .addPieces(new Point(4, 2), new Rabbit(this))
                .addPieces(new Point(3, 0), new Rabbit(this))

                .addPieces(new Point(2, 4), new Mushroom())
                .addPieces(new Point(3, 1), new Mushroom())

                .addPieces(new Point(1, 1), new Fox(this), Direction.UP)
                .addPieces(new Point(4, 3), new Fox(this), Direction.LEFT)
                .build());
    }

    boolean applyLevel(int levelNumber) {
        if (levels.containsKey(levelNumber)) {
            board = levels.get(levelNumber);
            return true;
        } else {
            return false;
        }
    }

    void movePiece(Point oldLoc, Point newLoc) {
        assert hasPiece(oldLoc);
        assert !hasPiece(newLoc);
        board.getSquare(newLoc).setPiece(board.getSquare(oldLoc).getPiece());
        board.getSquare(oldLoc).removePiece();
    }

    Piece getPiece(Point loc) {
        assert board.hasSquare(loc);
        return board.getSquare(loc).getPiece();
    }

    boolean hasPiece(Point loc) {
        return board.hasSquare(loc) && board.getSquare(loc).hasPiece();
    }

    String getSquareAsString(Point loc) {
        return (board.hasSquare(loc)) ? board.getSquare(loc).toString() : "Empty";
    }

    @Override
    public String toString() {
        return board.toString();
    }
}
