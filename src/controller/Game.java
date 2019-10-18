package controller;

import model.Board;
import model.Move;
import model.Piece;
import model.UserCommand;
import view.Observer;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * The main controller of the program.
 * Each of the functions in the game function similar to a "in game command"
 */
public class Game {
    /**
     * A reference to the board modal
     */
    private Board board;

    /**
     * The level number of the game
     */
    private int levelNumber;

    /**
     * Sets up the board with the given observer, and level
     * @param observer a view
     * @param levelNumber an int between 1 to 6
     */
    public void setUp(Observer observer, int levelNumber) {
        this.levelNumber = levelNumber;
        switch (levelNumber) {
            case 1 -> board = new Board.Builder(true)
                    .addPieces(new Point(2, 3), Piece.RABBIT)

                    .addPieces(new Point(0, 1), Piece.MUSHROOM)
                    .addPieces(new Point(0, 2), Piece.MUSHROOM)
                    .addPieces(new Point(1, 3), Piece.MUSHROOM)
                    .build();
            case 2 -> board = new Board.Builder(true)
                    .addPieces(new Point(0, 2), Piece.RABBIT)
                    .addPieces(new Point(0, 4), Piece.RABBIT)

                    .addPieces(new Point(0, 3), Piece.MUSHROOM)
                    .addPieces(new Point(1, 4), Piece.MUSHROOM)
                    .addPieces(new Point(2, 3), Piece.MUSHROOM)
                    .build();
            case 3 -> board = new Board.Builder(true)
                    .addPieces(new Point(0, 1), Piece.RABBIT)
                    .addPieces(new Point(4, 3), Piece.RABBIT)

                    .addPieces(new Point(0, 3), Piece.MUSHROOM)
                    .addPieces(new Point(1, 2), Piece.MUSHROOM)
                    .addPieces(new Point(3, 2), Piece.MUSHROOM)
                    .build();
            case 4 -> board = new Board.Builder(true)
                    .addPieces(new Point(2, 0), Piece.RABBIT)
                    .addPieces(new Point(4, 1), Piece.RABBIT)

                    .addPieces(new Point(3, 0), Piece.MUSHROOM)
                    .addPieces(new Point(3, 2), Piece.MUSHROOM)
                    .addPieces(new Point(4, 3), Piece.MUSHROOM)
                    .build();
            case 20 -> board = new Board.Builder(true)
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
            case 60 -> board = new Board.Builder(true)
                    .addPieces(new Point(1, 3), Piece.RABBIT)
                    .addPieces(new Point(2, 4), Piece.RABBIT)
                    .addPieces(new Point(4, 3), Piece.RABBIT)

                    .addPieces(new Point(0, 3), Piece.MUSHROOM)
                    .addPieces(new Point(2, 2), Piece.MUSHROOM)
                    .addPieces(new Point(3, 0), Piece.MUSHROOM)

                    .addPieces(new Point(1, 1), Piece.FOX_PLUS_Y)
                    .addPieces(new Point(1, 0), Piece.FOX_MINUS_Y)
                    .build();
            default -> board = new Board.Builder(true)
                    .build();
        }
        board.setObserver(observer);
        draw();
    }

    /**
     * Resets the game board
     */
    @UserCommand(description = "Reset the game board")
    public void reset() {
        setUp(board.getObserver(), levelNumber);
    }

    /**
     * Changes the level number and resets the game board
     * @param levelNumber - the level number
     */
    @UserCommand(description = "Change the game level")
    public void changeLevel(int levelNumber) {
        setUp(board.getObserver(), levelNumber);
    }

    /**
     * Exits the game
     */
    @UserCommand(description = "Exit the game")
    public void exit() {
        System.exit(0);
    }

    /**
     * Processes the game with the given move. If the move is invalid, the method will do nothing and return false.
     * @param move move to process
     * @return true if the move is valid
     */
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

    /**
     * Re-updates the view through board.notifyObserver()
     */
    @UserCommand(description = "Redraw the game board")
    public void draw() {
        board.notifyObserver();
    }

    /**
     * Draws all possible moves for a given piece at a given point
     * @param point the location of the piece
     */
    @UserCommand(description = "Draw all possible move for a piece")
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

    /**
     * Returns if the user has won
     * @return true if the user has won
     */
    public boolean isVictory() {
        return board.isVictory();
    }

}
