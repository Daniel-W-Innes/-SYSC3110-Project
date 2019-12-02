package controller;

import helpers.Graph;
import helpers.Move;
import helpers.Point;
import helpers.Proto;
import model.Board;
import view.Gui;
import view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;

import static helpers.GameBuilder.getStartingBoard;

/**
 * The main controller of the program.
 * Each of the functions in the game function similar to a "in game command"
 */
public class Game {
    public static final String resourcesFolder = "resources" + File.separator;
    private static final String STARTING_LEVEL_NAME = "20";
    private Graph graph;
    private final Stack<Move> undoHistory;
    private final Stack<Move> redoHistory;

    /**
     * A reference to the board model
     */
    private Board board;
    /**
     * The level number of the game
     */
    private String levelName;

    /**
     * Initializes the game with the given starting level.
     *
     * @param startingLevelName the initial level to load
     * @throws IOException
     */

    public Game(String startingLevelName) throws IOException {
        undoHistory = new Stack<>();
        redoHistory = new Stack<>();
        graph = new Graph();
        setLevel(new Gui(this), startingLevelName);
        graph.genSolution(board);

    }

    public static void main(String[] args) throws IOException {
        new Game(STARTING_LEVEL_NAME);
    }

    /**
     * Moves the point at the start point of move to the end point of move.
     *
     * @param move the move representing the change being applied to the board
     */

    public void movePiece(Move move) {
        movePiece(move, true);
        // After a user has moved, the redo history is invalidated
        redoHistory.clear();
    }

    /**
     * Helper function to process the move request.
     *
     * @param move             the move representing the change being applied to the board
     * @param addToUndoHistory whether to store an equivalent undo command for the move
     */

    /*
        There are two cases to consider with the hint and undo buttons:
        1. The user moves with the correct move. In that case, regardless if a hint was requested, the graph can continue using
           then next hint move.

        2. The user moves incorrectly, or applies a move that is not the same as the next hint, such as if the undo button is pressed.
           In that case, regardless if a hint was requested, the solution is regenerated.  The history of moves
           is kept, but none of the history is synchronized with the solution, as the history was made before the new solution was created.
     */
    void movePiece(Move move, boolean addToUndoHistory) {
        // The user saw the possible moves for the piece that was clicked, and selected a new location for the piece.
        // It is time to apply to the piece that was previously queried for valid moves.
        board.movePiece(board.getPieces().get(move.getStartPoint()), move.getEndPoint(), true);

        // Must be called before the advance solution index!
        Optional<Move> hintMove = graph.getHintMove();

        // The solution has to remain synchronized by pointing to the next hint. Note that this is redundant if an incorrect move is made. See below hintMove.equals() check.
        // The user has moved away from the correct solution; therefore the current solution is invalid and has to be redone.
        if (hintMove.isEmpty() || !hintMove.get().equals(move)) {
            graph.genSolution(board);
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

    public Set<Point> getEndPoint(Point point) {
        if (board.hasPiece(point)) {
            return board.getPieces().get(point).getEndPoint(board, point);
        } else {
            return new HashSet<>();
        }
    }

    /**
     * Set the level of game to the passed in level.
     *
     * @param observer    the view reference
     * @param levelName the level to set the game to
     */

    public void setLevel(View observer, String levelName) throws IOException {
        board = getStartingBoard(levelName);
        graph.genSolution(board);
        cleanGame(observer, levelName);
    }

    /**
     * Helper function to aid in saving the game.
     *
     * @return
     */

    private Proto.Game toProto() {
        return Proto.Game.newBuilder()
                .setBoard(board.toProto())
                .setLevelName(levelName)
                .setGraph(graph.toProto())
                .build();
    }

    /**
     * Save the current state of the game to a file, that can later loaded for playing.
     *
     * @param fileName name of the file holding the level
     * @throws IOException
     */

    public void save(String fileName) throws IOException {
        toProto().writeTo(new FileOutputStream(fileName));
    }

    /**
     * Load the specified the game so that it can be played.
     *
     * @param observer reference to the view where the board should be displayed
     * @param fileName name of the text file that holds the level to be loaded
     * @throws IOException
     */

    public void load(View observer, String fileName) throws IOException {
        Proto.Game game = Proto.Game.parseFrom(new FileInputStream(fileName));
        board = new Board(game.getBoard());
        graph = new Graph(game.getGraph(), board);
        cleanGame(observer, game.getLevelName());
    }

    /**
     * Clears the game of any previous game history
     *
     * @param observer  reference to the view where the board should be displayed
     * @param levelName the name of the level that is being played
     */

    private void cleanGame(View observer, String levelName) {
        this.levelName = levelName;
        observer.sendInitialBoard(board);
        board.setView(observer);
        undoHistory.clear();
        redoHistory.clear();
    }

    /**
     * Reset the game to the passed in level.
     *
     * @param observer A view to add to the new board
     */

    public void resetLevel(View observer) throws IOException {
        setLevel(observer, levelName);
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
            movePiece(redoHistory.pop().getReverse(), true);
        }
    }

    /**
     *  Undo a move that was previously made.
     */
    public void undo() {
        if (!undoHistory.empty()) {
            Move move = undoHistory.pop().getReverse();
            movePiece(move, false);
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