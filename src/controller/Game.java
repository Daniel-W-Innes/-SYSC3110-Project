package controller;

import helpers.Move;
import helpers.Piece;
import model.Board;
import view.GuiView;
import view.View;

import java.awt.*;
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

    private Piece lastClickedPiece = null;

    /**
     * Sets up the board with the given observer, and level
     * @param observer A view
     * @param levelNumber A level number from the book. Note: not all of those are available.
     */
    //TODO: Refactor into new class
    public void setUp(View observer, int levelNumber) {
        this.levelNumber = levelNumber;
        board = getStartingBoard(levelNumber);
        observer.sendInitialBoard(board);
        this.board.setView(observer);
    }

    public List<Move> getMoves(Point p) {

        /*
            When a user clicks a location on the view, a point is generated. If there are any
            corresponding pieces at that given point, then the list of possible moves are passed to the view.
         */

        lastClickedPiece = board.getPieces().get(p);
        if(lastClickedPiece == null) {
            return new ArrayList<>();
        }

        return this.board.getPieces().get(p).getMoves(board);
    }

    public void movePiece(Move move) {
        // The user saw the possible moves for the piece that was clicked, and selected a new location for the piece.
        // It is time to apply to the piece that was previously queried for valid moves.
        board.movePiece(lastClickedPiece, move.getEndPoint());
    }


    public static void main(String[] args) {
        Game game = new Game();
        game.setUp(new GuiView(game), 3);
    }
}
