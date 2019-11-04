package controller;

import helpers.Move;
import helpers.Piece;
import model.Board;
import view.Gui;
import view.View;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static helpers.GameBuilder.getStartingBoard;

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

    public static final String resourcesFolder = "resources" + File.separator;
    private Piece lastClickedPiece;

    public static void main(String[] args) {
        Game game = new Game();
        game.setUp(new Gui(game), 1);
    }

    /**
     * Sets up the board with the given observer, and level
     * @param observer A view
     * @param levelNumber A level number from the book. Note: not all of those are available.
     */
    public void setUp(View observer, int levelNumber) {
        setLevel(observer, levelNumber);
    }

    public void movePiece(Move move) {
        // The user saw the possible moves for the piece that was clicked, and selected a new location for the piece.
        // It is time to apply to the piece that was previously queried for valid moves.
        board.movePiece(lastClickedPiece, move.getEndPoint());
    }

    public List<Move> getMoves(Point point) {
        // When a user clicks a location on the view, a point is generated. If there are any
        // corresponding pieces at that given point, then the list of possible moves are passed to the view.
        lastClickedPiece = board.getPieces().get(point);
        if (null == lastClickedPiece) {
            return new ArrayList<>();
        }

        return board.getPieces().get(point).getMoves(board, point);
    }

    public void setLevel(View observer, int levelNumber) {
        this.levelNumber = levelNumber;
        board = getStartingBoard(levelNumber);
        observer.sendInitialBoard(board);
        board.setView(observer);
    }

    public void resetLevel(View observer) {
        setLevel(observer, levelNumber);
    }
}
