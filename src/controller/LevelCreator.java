package controller;

import helpers.*;
import model.Board;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


//Singleton builder?
public class LevelCreator {
    private static Board board;

    //Static initializer for the board
    static {
        //Default terrain only.
        board = new Board();
        GameBuilder.setDefaultTerrain(board);
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
    public static List<Point> getAvailableSpots(Piece piece) {
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
                                && point.x < board.maxBoardSize.x - 1 //If it fits horizontally
                                && (!terrain.get(point).isHole() || !terrain.get(new Point(point.x + 1, point.y)).isHole()) //Can't fit over Holes
                                && (!pieces.containsKey(point) || !pieces.containsKey(new Point(point.x + 1, point.y)))) //No pieces there
                        .collect(Collectors.toList());
            } else {
                //For Y-Foxes
                return spots.stream()
                        .filter(point -> (point.x == 1 || point.x == 3) //Y-Foxes only fit on columns 1 and 3
                                && point.y < board.maxBoardSize.y - 1 //If it fits vertically
                                && (!terrain.get(point).isHole() || !terrain.get(new Point(point.x, point.y + 1)).isHole()) //Can't fit over Holes
                                && (!pieces.containsKey(point) || !pieces.containsKey(new Point(point.x, point.y + 1)))) //No pieces there
                        .collect(Collectors.toList());
            }
        } else if (piece instanceof Rabbit) {
            //Rabbits can be placed anywhere except on other pieces
            return spots.stream()
                    .filter(point -> !pieces.containsKey(point))
                    .collect(Collectors.toList());
        } else if (piece instanceof Mushroom) {
            //Mushrooms can be placed anywhere except on pieces or holes
            return spots.stream()
                    .filter(point -> !pieces.containsKey(point) && !terrain.get(point).isHole())
                    .collect(Collectors.toList());
        } else {
            throw new IllegalStateException("Error: invalid piece type");
        }
    }

    /**
     * Clear everything off the Square, the piece and if it's a Hole
     * @param p the point on the board to clear
     */
    public static void clearSquare(Point p) {
        board.getPieces().remove(p); //Remove the Piece at "p" if it exists
        board.getTerrain().remove(p); //Remove the Square at "p" if it exists
    }

    /**
     * Clear everything from the board
     */
    public static void resetLevel() {
        board = new Board();
    }

    /**
     * Exports the board to a file. Call this when the user is done creating the level.
     */
    public static void saveLevel(String fileName) throws IOException {
        board.toProto().writeTo(new FileOutputStream(fileName));
    }

    public static void main(String[] args) {
        LevelCreator.getAvailableSpots(new Mushroom(new Point()));
    }
}
