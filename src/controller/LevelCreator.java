package controller;

import helpers.GameBuilder;
import helpers.Graph;
import helpers.Piece;
import helpers.Point;
import model.Board;
import view.View;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


//Singleton builder?
public class LevelCreator {
    private static Board board;
    private static final Set<Point> spots;

    static {
        board = new Board();
        GameBuilder.setDefaultTerrain(board);
        spots = IntStream.range(0, board.maxBoardSize.x)
                .boxed()
                .flatMap(x -> IntStream.range(0, board.maxBoardSize.y)
                        .mapToObj(y -> new Point(x, y)))
                .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Reset the game being created to an empty board.
     */

    public static void resetLevel() {
        board = new Board();
    }

    /**
     * Determines if there is a solution for the given board being constructed.
     *
     * @return true if there is a solution
     * @throws InterruptedException
     */

    public static boolean solutionExists() throws InterruptedException {
        Graph solution = new Graph();
        solution.genSolution(board);
        solution.getThread().join();
        return solution.getHintMove().isPresent();
    }

    /**
     * Get the board that is being built.
     *
     * @return board that is being constructed
     */

    public static Board getBoard() {
        return board;
    }

    /**
     * Displays the game being created instead of the game that was previously loaded.
     *
     * @param view reference to the view where the game should be displayed
     */

    public static void showGameBeingCreated(View view) {
        board = new Board();
        GameBuilder.setDefaultTerrain(board);
        board.setView(view);
        view.sendInitialBoard(board);
    }

    /**
     * Since the front-end should already know the valid placements, this method won't do any checking.
     * @param p the Piece to place on the Board
     */
    public static void placePiece(Piece p) {
        board.addPiece(p);
    }

    /**
     * Gets a list of Points where you can put the provided Piece.
     * @param piece The piece you want to check the available spots.
     * @return a list of valid Points where you can place the piece
     */
    public static Set<Point> getAvailableSpots(Piece piece) {
        return piece.getAvailableSpots(board, spots);
    }

    /**
     * Clear everything off the Square, the piece and if it's a Hole
     * @param p the point on the board to clear
     */
    public static void clearSquare(Point p, boolean applyChangesView) {
        board.removePiece(p, applyChangesView);
    }

    /**
     * Exports the board to a file. Call this when the user is done creating the level.
     */
    public static boolean saveLevel(String fileName) throws IOException, InterruptedException {
        if (solutionExists()) {
            board.save(fileName);
            return true;
        } else {
            return false;
        }
    }
}