package controller;

import helpers.*;
import model.Board;
import view.GuiView;
import view.View;

import java.awt.Point;
import java.net.MalformedURLException;
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
    //TODO: Refactor into new class
    public void setUp(View observer, int levelNumber) {
        this.levelNumber = levelNumber;
        switch (levelNumber) {
            case 1:
                board = new Board();

                board.addPiece(new Point(2, 3), new Rabbits(new Point(2, 3)));

                board.addPiece(new Point(0, 1), new Mushroom(new Point(0, 1)));
                board.addPiece(new Point(0, 2), new Mushroom(new Point(0, 2)));
                board.addPiece(new Point(1, 3), new Mushroom(new Point(1, 3)));
                this.setDefaultTerrain(board);
                break;
            case 2:
                board = new Board();
                board.addPiece(new Point(4, 2), new Rabbits((new Point(4, 2))));
                board.addPiece(new Point(2, 4), new Rabbits((new Point(2, 4))));

                board.addPiece(new Point(3, 1), new Foxes(Foxes.Direction.Y_AXIS, new Point(3, 1)));

                board.addPiece(new Point(0, 1), new Mushroom(new Point(0, 1)));
                board.addPiece(new Point(1, 2), new Mushroom(new Point(1, 2)));
                board.addPiece(new Point(2, 3), new Mushroom(new Point(2, 3)));
                this.setDefaultTerrain(board);
                break;
            case 3:
                board = new Board();
                board.addPiece(new Point(3, 0), new Rabbits(new Point(3, 0)));
                board.addPiece(new Point(4, 2), new Rabbits(new Point(4, 2)));
                board.addPiece(new Point(1, 4), new Rabbits(new Point(1, 4)));

                board.addPiece(new Point(1, 1), new Foxes(Foxes.Direction.Y_AXIS, new Point(1, 1)));
                board.addPiece(new Point(4, 3), new Foxes(Foxes.Direction.X_AXIS, new Point(4, 3)));

                board.addPiece(new Point(3, 1), new Mushroom(new Point(3, 1)));
                board.addPiece(new Point(2, 4), new Mushroom(new Point(2, 4)));
                this.setDefaultTerrain(board);
                break;
        }

        view = observer;
        view.sendInitialBoard(board);
        this.board.setView(view);
    }
    private void setDefaultTerrain(Board b) {
        board.addSquare(new Point(0,0), new Square(true, true));
        board.addSquare(new Point(2,0), new Square(false, true));
        board.addSquare(new Point(4,0), new Square(true, true));

        board.addSquare(new Point(0,2), new Square(false, true));
        board.addSquare(new Point(2,2), new Square(true, true));
        board.addSquare(new Point(4,2), new Square(false, true));

        board.addSquare(new Point(0,4), new Square(true, true));
        board.addSquare(new Point(2,4), new Square(false, true));
        board.addSquare(new Point(4,4), new Square(true, true));
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
        if(isVictory()) {
            this.view.notifyWin();
        }
    }

    /**
     * Returns if the user has won
     * @return true if the user has won
     */
    public boolean isVictory() {
        return board.isVictory();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.setUp(new GuiView(game), 3);
    }
}
