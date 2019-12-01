package controller;

import helpers.*;
import model.Board;
import view.View;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


//Singleton builder?
public class LevelCreator {
    //Package private so it can be unit tested
    static Board board;
    static Graph solution;

    //Static initializer for the board
    static {
        //Default terrain only.
        board = new Board();
        GameBuilder.setDefaultTerrain(board);
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
        solution = new Graph();
        solution.genSolution(board);
        Thread.sleep(500);
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
        //Start with all points
        List<Point> spots = new ArrayList<>();
        for (int x = 0; x < board.maxBoardSize.x; x++) {
            for (int y = 0; y < board.maxBoardSize.y; y++) {
                spots.add(new Point(x, y));
            }
        }
        Map<Point, Piece> pieces = board.getPieces();
        Map<Point, Square> terrain = board.getTerrain();

        if (piece instanceof Fox) {
            Fox fox = ((Fox) piece);
            if (fox.getDirection() == Direction.X_AXIS) {
                //Fox X-Foxes
                return spots.stream()
                        .filter(point -> (point.y == 1 || point.y == 3) //X-Foxes only fit on rows 1 and 3
                                && point.x > 0 //If it fits horizontally
                                && (!terrain.containsKey(point) || !terrain.get(point).isHole())
                                && (!terrain.containsKey(new Point(point.x - 1, point.y)) || !terrain.get(new Point(point.x - 1, point.y)).isHole()) //Can't fit over Holes
                                && (!pieces.containsKey(point) && !pieces.containsKey(new Point(point.x - 1, point.y)))) //No pieces there
                        .collect(Collectors.toSet());
            } else {
                //For Y-Foxes
                return spots.stream()
                        .filter(point -> (point.x == 1 || point.x == 3) //Y-Foxes only fit on columns 1 and 3
                                && point.y > 0 //If it fits vertically
                                && (!terrain.containsKey(point) || !terrain.get(point).isHole())
                                && (!terrain.containsKey(new Point(point.x, point.y - 1)) || !terrain.get(new Point(point.x, point.y - 1)).isHole()) //Can't fit over Holes
                                && (!pieces.containsKey(point) && !pieces.containsKey(new Point(point.x, point.y - 1)))) //No pieces there
                        .collect(Collectors.toSet());
            }
        } else if (piece instanceof Rabbit) {
            //Rabbits can be placed anywhere except on other pieces
            return spots.stream()
                    .filter(point -> !pieces.containsKey(point))
                    .collect(Collectors.toSet());
        } else if (piece instanceof Mushroom) {
            //Mushrooms can be placed anywhere except on pieces or holes
            return spots.stream()
                    .filter(point -> !pieces.containsKey(point))
                    .filter(point -> !terrain.containsKey(point) || !terrain.get(point).isHole())
                    .collect(Collectors.toSet());
        } else {
            throw new IllegalStateException("Error: invalid piece type");
        }
    }

    /**
     * Clear everything off the Square, the piece and if it's a Hole
     * @param p the point on the board to clear
     * @param applyChangesView true if removing the changes should be made to the view
     */
    public static void clearSquare(Point p, boolean applyChangesView) {
        board.removePiece(p, applyChangesView);
    }

    /**
     * Exports the board to a file. Call this when the user is done creating the level.
     */
    public static void saveLevel(String fileName) throws IOException, InterruptedException {
        solutionExists();
        toProto(fileName, solution).writeTo(new FileOutputStream(fileName));
    }

    public static void main(String[] args) {
        LevelCreator.getAvailableSpots(new Mushroom(new Point()));
    }

    /**
     * Internal helper to aid in saving the game
     *
     * @param levelName name of the level to save
     * @param graph     solution of the level that is being saved
     * @return
     */

    private static Proto.Game toProto(String levelName, Graph graph) {
        return Proto.Game.newBuilder()
                .setBoard(board.toProto())
                .setLevelName(levelName)
                .setGraph(graph.toProto())
                .build();
    }
}