package controller;

import model.Board;
import model.Move;
import model.Piece;
import view.Observer;

import java.awt.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Map;

public class Game {
    private Board board;


    private void addView(Observer observer) {
        if (board != null) {
            board.setObserver(observer);
        }
    }

    public void setUp(Observer observer) {
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
        addView(observer);
        draw();
    }

    @UserCommand(description = "Reset the game board")
    public void reset() {
        setUp(board.getObserver());
    }

    @UserCommand(description = "Exit the game")
    public void exit() {
        board.getObserver().exit();
    }

    @UserCommand(description = "Move a game piece from one location to another")
    public boolean move(Move move) {
        if (board.hasSquare(move.getStartPoint()) && board.getSquare(move.getStartPoint()).hasPiece()) {
            switch (board.getSquare(move.getStartPoint()).getPiece()) {
                case RABBIT -> {
                    return Rabbits.checkAndMove(board, move);
                }
                case FOX_PLUS_X, FOX_PLUS_Y, FOX_MINUS_X, FOX_MINUS_Y -> {
                    return Foxes.checkAndMove(board, move);
                }
            }
        }
        return false;
    }

    @UserCommand(description = "redraw the game board")
    public void draw() {
        board.notifyObserver();
    }

    @UserCommand(description = "draw all possible move for a piece")
    public void getMoves(Point point) {
        if (board.hasSquare(point) && board.getSquare(point).hasPiece()) {
            Map<Move, Board> moves = new HashMap<>();
            switch (board.getSquare(point).getPiece()) {
                case RABBIT -> moves = Rabbits.getMoves(board, point);
                case FOX_PLUS_X, FOX_PLUS_Y, FOX_MINUS_X, FOX_MINUS_Y -> moves = Foxes.getMoves(board, point);
            }
            moves.values().forEach(Board::notifyObserver);
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface UserCommand {
        String description();
    }
}
