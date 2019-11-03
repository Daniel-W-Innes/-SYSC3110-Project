package controller;

import helpers.*;
import model.Board;
import view.GuiView;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Game {
    /**
     * A reference to the board model
     */
    private Board board;

    /**
     * The level number of the game
     */
    private int levelNumber;

    public Game() {
        this.setUp(1);
    }
    /**
     * Sets up the board with the given observer, and level
     * @param levelNumber A level number from the book. Note: not all of those are available.
     */
    public void setUp(int levelNumber) {
        this.levelNumber = levelNumber;
//        switch (levelNumber) {
//            case 1 -> board = new Board.Builder(true)
//                    .addPieces(new Point(2, 3), Piece.RABBIT)
//
//                    .addPieces(new Point(0, 1), Piece.MUSHROOM)
//                    .addPieces(new Point(0, 2), Piece.MUSHROOM)
//                    .addPieces(new Point(1, 3), Piece.MUSHROOM)
//                    .build();
//            case 2 -> board = new Board.Builder(true)
//                    .addPieces(new Point(0, 2), Piece.RABBIT)
//                    .addPieces(new Point(0, 4), Piece.RABBIT)
//
//                    .addPieces(new Point(0, 3), Piece.MUSHROOM)
//                    .addPieces(new Point(1, 4), Piece.MUSHROOM)
//                    .addPieces(new Point(2, 3), Piece.MUSHROOM)
//                    .build();
//            case 3 -> board = new Board.Builder(true)
//                    .addPieces(new Point(0, 1), Piece.RABBIT)
//                    .addPieces(new Point(4, 3), Piece.RABBIT)
//
//                    .addPieces(new Point(0, 3), Piece.MUSHROOM)
//                    .addPieces(new Point(1, 2), Piece.MUSHROOM)
//                    .addPieces(new Point(3, 2), Piece.MUSHROOM)
//                    .build();
//            case 4 -> board = new Board.Builder(true)
//                    .addPieces(new Point(2, 0), Piece.RABBIT)
//                    .addPieces(new Point(4, 1), Piece.RABBIT)
//
//                    .addPieces(new Point(3, 0), Piece.MUSHROOM)
//                    .addPieces(new Point(3, 2), Piece.MUSHROOM)
//                    .addPieces(new Point(4, 3), Piece.MUSHROOM)
//                    .build();
//            case 20 -> board = new Board.Builder(true)
//                    .addPieces(new Point(1, 4), Piece.RABBIT)
//                    .addPieces(new Point(4, 2), Piece.RABBIT)
//                    .addPieces(new Point(3, 0), Piece.RABBIT)
//
//                    .addPieces(new Point(2, 4), Piece.MUSHROOM)
//                    .addPieces(new Point(3, 1), Piece.MUSHROOM)
//
//                    .addPieces(new Point(1, 1), Piece.FOX_PLUS_Y)
//                    .addPieces(new Point(1, 0), Piece.FOX_MINUS_Y)
//                    .addPieces(new Point(4, 3), Piece.FOX_PLUS_X)
//                    .addPieces(new Point(3, 3), Piece.FOX_MINUS_X)
//                    .build();
//            case 60 -> board = new Board.Builder(true)
//                    .addPieces(new Point(1, 3), Piece.RABBIT)
//                    .addPieces(new Point(2, 4), Piece.RABBIT)
//                    .addPieces(new Point(4, 3), Piece.RABBIT)
//
//                    .addPieces(new Point(0, 3), Piece.MUSHROOM)
//                    .addPieces(new Point(2, 2), Piece.MUSHROOM)
//                    .addPieces(new Point(3, 0), Piece.MUSHROOM)
//
//                    .addPieces(new Point(1, 1), Piece.FOX_PLUS_Y)
//                    .addPieces(new Point(1, 0), Piece.FOX_MINUS_Y)
//                    .build();
//            default -> board = new Board.Builder(true)
//                    .build();
//        }
        System.out.println(board);
        //board.setObserver(observer);
        //draw();
    }
    /**
     * Gets all possible moves for a given piece at a given point
     * @param point the location of the piece
     */
    public List<Move> getMoves(Point point) {
        //TODO: implement this method
        return List.of(new Move(point, new Point(point.x + 1, point.y)),
                new Move(point, new Point(point.x - 1, point.y)));
    }

    /**
     * ???
     * @param move
     * @return
     */
    public boolean move(Move move) {
        return true;
        //return this.board.move(move); TODO: implement stub. Implement by delegating to Board?
    }

    /**
     * Returns if the user has won
     * @return true if the user has won
     */
    public boolean isVictory() {
        return board.isVictory();
    }

    /**
     * Main method - Used to start the game
     *
     * @param args - These arguments are not used
     */
    public static void main(String[] args) {
        GuiView guiView = new GuiView(new Game());
    }

}
