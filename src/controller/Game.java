package controller;

import model.Board;
import model.Move;
import model.Piece;
import view.TextView;

import java.awt.*;

public class Game {
    private Board board;

    public void setUp(TextView textView) {
        board = new Board.Builder()
                .addHole(new Point(0, 0))
                .addHole(new Point(4, 4))
                .addHole(new Point(0, 4))
                .addHole(new Point(4, 0))
                .addHole(new Point(2, 2))

                .addRaisedSquare(new Point(0, 2))
                .addRaisedSquare(new Point(2, 0))
                .addRaisedSquare(new Point(2, 4))
                .addRaisedSquare(new Point(4, 2))

                .addPieces(new Point(1, 4), Piece.RABBIT)
                .addPieces(new Point(4, 2), Piece.RABBIT)
                .addPieces(new Point(3, 0), Piece.RABBIT)

                .addPieces(new Point(2, 4), Piece.MUSHROOM)
                .addPieces(new Point(3, 1), Piece.MUSHROOM)

                .addPieces(new Point(1, 1), Piece.FOX_PLUS_Y)
                .addPieces(new Point(1, 0), Piece.FOX_MINUS_Y)
                .addPieces(new Point(4, 3), Piece.FOX_PLUS_X)
                .addPieces(new Point(3, 3), Piece.FOX_MINUS_X)
                .build();
        board.setObserver(textView);
        board.notifyObserver();
    }

    public boolean move(Move move) {
        if (board.hasSquare(move.getStartPoint()) && board.getSquare(move.getStartPoint()).hasPiece()) {
            switch (board.getSquare(move.getStartPoint()).getPiece()) {
                case RABBIT:
                    return Rabbits.checkAndMove(board, move);
                case FOX_PLUS_X:
                case FOX_PLUS_Y:
                case FOX_MINUS_X:
                case FOX_MINUS_Y:
                    return Foxes.checkAndMove(board, move);
            }
        }
        return false;
    }

    public void draw() {
        board.notifyObserver();
    }
}
