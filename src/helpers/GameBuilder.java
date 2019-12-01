package helpers;

import model.Board;

import java.io.IOException;

/**
 * Helper class to initialize the populate the board with pieces, thus creating an initial boars state.
 */

public class GameBuilder {

    /**
     * Constructs a initial board state based off of the level passed in.
     *
     * @param levelName the desired level, and thus difficulty, of the game
     * @return an instance of Board initialized with pieces and terrain corresponding to the level passed in
     */

    public static Board getStartingBoard(String levelName) throws IOException {
        Board board = new Board();
        boolean loadFromFile = false;
        switch (levelName) {
            case "1" -> {
                board.addPiece(new Rabbit(new Point(2, 3)));
                board.addPiece(new Mushroom(new Point(0, 1)));
                board.addPiece(new Mushroom(new Point(0, 2)));
                board.addPiece(new Mushroom(new Point(1, 3)));
                setDefaultTerrain(board);
            }
            case "20" -> {
                board.addPiece(new Rabbit((new Point(1, 4))));
                board.addPiece(new Rabbit((new Point(4, 2))));
                board.addPiece(new Rabbit((new Point(3, 0))));

                board.addPiece(new Fox(Direction.Y_AXIS, new Point(1, 1)));
                board.addPiece(new Fox(Direction.X_AXIS, new Point(4, 3)));

                board.addPiece(new Mushroom(new Point(2, 4)));
                board.addPiece(new Mushroom(new Point(3, 1)));

                setDefaultTerrain(board);
            }
            case "2" -> {
                board.addPiece(new Rabbit((new Point(4, 2))));
                board.addPiece(new Rabbit((new Point(2, 4))));

                board.addPiece(new Fox(Direction.X_AXIS, new Point(3, 1)));

                board.addPiece(new Mushroom(new Point(0, 1)));
                board.addPiece(new Mushroom(new Point(1, 2)));
                board.addPiece(new Mushroom(new Point(2, 3)));
                setDefaultTerrain(board);
            }
            case "3" -> {
                board.addPiece(new Rabbit(new Point(3, 0)));
                board.addPiece(new Rabbit(new Point(4, 2)));
                board.addPiece(new Rabbit(new Point(1, 4)));

                board.addPiece(new Fox(Direction.X_AXIS, new Point(1, 1)));
                board.addPiece(new Fox(Direction.Y_AXIS, new Point(3, 3)));

                board.addPiece(new Mushroom(new Point(3, 1)));
                board.addPiece(new Mushroom(new Point(2, 4)));
                setDefaultTerrain(board);
            }
            case "60" -> {
                board.addPiece(new Rabbit(new Point(1, 3)));
                board.addPiece(new Rabbit(new Point(2, 4)));
                board.addPiece(new Rabbit(new Point(4, 3)));

                board.addPiece(new Fox(Direction.Y_AXIS, new Point(1, 1)));

                board.addPiece(new Mushroom(new Point(0, 3)));
                board.addPiece(new Mushroom(new Point(2, 2)));
                board.addPiece(new Mushroom(new Point(3, 0)));
                setDefaultTerrain(board);
            }
            default -> loadFromFile = true;
        }
        if (loadFromFile) {
            board = new Board(levelName);
        }
        return board;
    }

    /**
     * Helper function to create the initial terrain of the board
     *
     * @param board the board to populate with terrain squares
     */

    public static void setDefaultTerrain(Board board) {
        board.addSquare(new Point(0, 0), new Square(true));
        board.addSquare(new Point(2, 0), new Square(false));
        board.addSquare(new Point(4, 0), new Square(true));

        board.addSquare(new Point(0, 2), new Square(false));
        board.addSquare(new Point(2, 2), new Square(true));
        board.addSquare(new Point(4, 2), new Square(false));

        board.addSquare(new Point(0, 4), new Square(true));
        board.addSquare(new Point(2, 4), new Square(false));
        board.addSquare(new Point(4, 4), new Square(true));
    }
}
