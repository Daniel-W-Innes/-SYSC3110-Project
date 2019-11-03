package controller;

import helpers.*;
import model.Board;
import view.View;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    private View view;

    private Piece lastClickedPiece = null;

    /**
     * Sets up the board with the given observer, and level
     * @param observer A view
     * @param levelNumber A level number from the book. Note: not all of those are available.
     */
    public void setUp(View observer, int levelNumber) {
        this.levelNumber = levelNumber;
        switch (levelNumber) {
            case 1:
                board = new Board();

                board.addPiece(new Point(2, 3), new Rabbits(new Point(2, 3)));

                board.addPiece(new Point(0, 1), new Mushroom(new Point(0, 1)));
                board.addPiece(new Point(0, 2), new Mushroom(new Point(0, 2)));
                board.addPiece(new Point(1, 3), new Mushroom(new Point(1, 3)));

        }

        view = observer;
    }

    public List<Move> getMoves(Point clickedLocation) {

        /*
            When a user clicks a location on the view, a point is generated. If there are any
            corresponding pieces at that given point, then the list of possible moves are passed to the view.
         */

        lastClickedPiece = board.getPieces().get(clickedLocation);
        if(lastClickedPiece == null) {
            return new ArrayList<>();
        }
        return lastClickedPiece.getMoves(board);
    }

    public void movePiece(Point newLocation) {
        // The user saw the possible moves for the piece that was clicked, and selected a new location for the piece.
        // It is time to apply to the piece that was previously queried for valid moves.
        board.movePiece(lastClickedPiece, newLocation);
    }

    /**
     * Returns if the user has won
     * @return true if the user has won
     */
    public boolean isVictory() {
        return board.isVictory();
    }

}
