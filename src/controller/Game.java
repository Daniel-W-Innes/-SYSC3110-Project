package controller;

import model.Board;
import model.Move;
import model.Piece;
import view.TextView;

import java.awt.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Game {
    private Board board;
    private TextView textView;


    private void addView(TextView textView) {
        this.textView = textView;
        if (board != null) {
            board.setObserver(textView);
        }
    }

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
        addView(textView);
        draw();
    }

    @Reflected(description = "description")
    public void setUp() {
        setUp(textView);
    }

    @Reflected(description = "description")
    public void exit() {
        textView.exit();
    }

    @Reflected(description = "description")
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

    @Reflected(description = "description")
    public void draw() {
        board.notifyObserver();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Reflected {
        String description();
    }
}
