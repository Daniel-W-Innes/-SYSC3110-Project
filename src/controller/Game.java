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

    public void movePiece(Move move) {
        movePiece(move, true);
        redoHistory.clear();
    }

    private void movePiece(Move move, boolean addToUndoHistory) {
        // The user saw the possible moves for the piece that was clicked, and selected a new location for the piece.
        // It is time to apply to the piece that was previously queried for valid moves.
        board.movePiece(board.getPieces().get(move.getStartPoint()), move.getEndPoint(), true);

        // Must be called before the advance solution index!
        Optional<Move> hintMove = graph.getHintMove();

        // The solution has to remain synchronized by pointing to the next hint. Note that this is redundant if an incorrect move is made. See below hintMove.equals() check.
        // The user has moved away from the correct solution; therefore the current solution is invalid and has to be redone.
        // This is deferred until the user presses "Hint" again, as otherwise this would cause a pause every time the user tries to solve the game differently than
        // the calculated solution.
        // TODO: Note that an optimization could be done where the user's moves are backtracked until a board a part of the original solution is achieved.
        if (hintMove.isEmpty() || !hintMove.get().equals(move)) {
            graph = new Graph(board);
        } else {
            graph.advanceSolutionIndex();
        }

        if (addToUndoHistory) {
            undoHistory.push(move);
        }
    }

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

        /*
        There are two cases to consider with the hint and undo buttons:
        1. The user moves with the correct move. In that case, regardless if a hint was requested, the graph can continue using
           then next hint move, or the back track move. An undo will have to be synchronized with the solution.
        2. The user moves incorrectly. In that case, regardless if a hint was requested, the solution is regenerated.  The history of moves
           is kept, but none of the history is synchronized with the solution, as the history was made before the new solution was created.
     */

    public Optional<Move> hint() {
        return graph.getHintMove();
    }

    public void redo() {
        if (!redoHistory.empty()) {
            movePiece(redoHistory.pop().getReverse(), true);
        }
    }

    public void undo() {
        if (!undoHistory.empty()) {
            Move move = undoHistory.pop().getReverse();
            movePiece(move, false);
            redoHistory.push(move);
        }
    }
}