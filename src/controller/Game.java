package controller;

import model.Board;
import model.MoveCommand;
import model.Piece;

import java.awt.*;

public class Game {
    private Board board;

    public void setUp() {
        board = new Board.Builder()
                .addTunnel(new Point(0, 0))
                .addTunnel(new Point(4, 4))
                .addTunnel(new Point(0, 4))
                .addTunnel(new Point(4, 0))
                .addTunnel(new Point(2, 2))

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
    }

    public boolean move(MoveCommand moveCommand) {
        if (board.hasSquare(moveCommand.getFrom()) && board.getSquare(moveCommand.getFrom()).hasPiece()) {
            switch (board.getSquare(moveCommand.getFrom()).getPiece()) {
                case RABBIT:
                    return Rabbits.checkMove(board, moveCommand);
                case FOX_PLUS_X:
                case FOX_PLUS_Y:
                case FOX_MINUS_X:
                case FOX_MINUS_Y:
                    return Foxes.checkMove(board, moveCommand);
            }
        }
        return false;
    }
}
