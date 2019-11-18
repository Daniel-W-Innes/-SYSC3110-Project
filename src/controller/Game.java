package controller;

import helpers.Graph;
import helpers.Move;
import model.Board;
import view.Gui;
import view.View;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

import static helpers.GameBuilder.getStartingBoard;

/**
 * The main controller of the program.
 * Each of the functions in the game function similar to a "in game command"
 */
public class Game {
    public static final String resourcesFolder = "resources" + File.separator;
    private static final int STARTING_LEVEL_NUMBER = 20;
    private Graph graph;
    private Stack<Move> undoHistory;
    private Stack<Move> redoHistory;
    /**
     * A reference to the board modal
     */
    private Board board;
    /**
     * The level number of the game
     */
    private int levelNumber;

    public static void main(String[] args) {
        Game game = new Game();
        game.setUp(new Gui(game), STARTING_LEVEL_NUMBER);
    }

    /**
     * Sets up the board with the given observer, and level.
     *
     * @param observer    A view to add to the new board
     * @param levelNumber A level number from the book. Note: not all of those are available.
     */
    public void setUp(View observer, int levelNumber) {
        setLevel(observer, levelNumber);
    }

    /**
     * Moves the point at the start point of move to the end point of move.
     *
     * @param move             the move representing the change being applied to the board
     * @param applyChangesView whether a move should be reflected on-screen. When dealing with the GUI it is true;
     *                         for internal logic such as a graph solution or tests it is false
     */

    public void movePiece(Move move, boolean applyChangesView) {
        movePiece(move, true, applyChangesView);
        // After a user has moved, the redo history is invalidated
        redoHistory.clear();
    }

    /**
     * Helper function to process the move request.
     *
     * @param move             the move representing the change being applied to the board
     * @param addToUndoHistory whether to store an equivalent undo command for the move
     * @param applyChangesView whether a move should be reflected on-screen. When dealing with the GUI it is true;
     */

    /*
        There are two cases to consider with the hint and undo buttons:
        1. The user moves with the correct move. In that case, regardless if a hint was requested, the graph can continue using
           then next hint move.

        2. The user moves incorrectly, or applies a move that is not the same as the next hint, such as if the undo button is pressed.
           In that case, regardless if a hint was requested, the solution is regenerated.  The history of moves
           is kept, but none of the history is synchronized with the solution, as the history was made before the new solution was created.
     */
    private void movePiece(Move move, boolean addToUndoHistory, boolean applyChangesView) {
        // The user saw the possible moves for the piece that was clicked, and selected a new location for the piece.
        // It is time to apply to the piece that was previously queried for valid moves.
        board.movePiece(board.getPieces().get(move.getStartPoint()), move.getEndPoint(), applyChangesView);

        // Must be called before the advance solution index!
        Optional<Move> hintMove = graph.getHintMove();

        // The solution has to remain synchronized by pointing to the next hint. Note that this is redundant if an incorrect move is made. See below hintMove.equals() check.
        // The user has moved away from the correct solution; therefore the current solution is invalid and has to be redone.
        if (hintMove.isEmpty() || !hintMove.get().equals(move)) {
            graph = new Graph(board);
        } else {
            graph.advanceSolutionIndex();
        }

        if (addToUndoHistory) {
            undoHistory.push(move);
        }
    }

    /**
     * Get the list of possible moves for the given point, which will be non-empty if there is a movable piece
     * at that point.
     *
     * @param point the point that was clicked on the GUI
     * @return a list of possible moves
     */

    public List<Move> getMoves(Point point) {
        if (board.hasPiece(point)) {
            return board.getPieces().get(point).getMoves(board, point);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Set the level of game to the passed in level.
     *
     * @param observer    the view reference
     * @param levelNumber the level to set the game to
     */

    public void setLevel(View observer, int levelNumber) {
        this.levelNumber = levelNumber;
        board = getStartingBoard(levelNumber);
        observer.sendInitialBoard(board);
        board.setView(observer);

        graph = new Graph(board);
        undoHistory = new Stack<>();
        redoHistory = new Stack<>();
    }

    /**
     * Reset the game to the passed in level.
     *
     * @param observer A view to add to the new board
     */

    public void resetLevel(View observer) {
        setLevel(observer, levelNumber);
    }

    /**
     * Gets the hint for the next move that has to be applied to get closer to the winning board.
     *
     * @return If there is a hint for the next move (solution has been generated and current board is not winning), then return that move.
     */

    public Optional<Move> hint() {
        return graph.getHintMove();
    }

    /**
     *  Redo an undo that was previously made.
     */

    public void redo() {
        if (!redoHistory.empty()) {
            movePiece(redoHistory.pop().getReverse(), true, true);
        }
    }

    /**
     *  Undo a move that was previously made.
     */
    public void undo() {
        if (!undoHistory.empty()) {
            Move move = undoHistory.pop().getReverse();
            movePiece(move, false, true);
            redoHistory.push(move);
        }
    }

    /**
     * Returns whether the current board is the victory board.
     *
     * @return true if the current board is a victory board.
     */
    public boolean gameWon() {
        return board.isVictory();
    }
}