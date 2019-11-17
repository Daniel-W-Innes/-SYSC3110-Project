package helpers;

import model.Board;

import java.awt.*;

/**
 * Helper class to initialize the populate the board with pieces, thus creating an initial boars state.
 */

public class GameBuilder {

    /**
     * Constructs a initial board state based off of the level passed in.
     *
     * @param levelNumber the desired level, and thus difficulty, of the game
     * @return an instance of Board initialized with pieces and terrain corresponding to the level passed in
     */

    public static Board getStartingBoard(int levelNumber) {
        Board board = new Board();
        switch (levelNumber) {
            case 1:
                board.addPiece(new Point(2, 3), new Rabbit(new Point(2, 3)));
                board.addPiece(new Point(0, 1), new Mushroom(new Point(0, 1)));
                board.addPiece(new Point(0, 2), new Mushroom(new Point(0, 2)));
                board.addPiece(new Point(1, 3), new Mushroom(new Point(1, 3)));
                setDefaultTerrain(board);
            break;
            case 20:
                board.addPiece(new Point(1, 4), new Rabbit((new Point(1, 4))));
                board.addPiece(new Point(4, 2), new Rabbit((new Point(4, 2))));
                board.addPiece(new Point(3, 0), new Rabbit((new Point(3, 0))));

                board.addPiece(new Point(1, 1), new Fox(Direction.Y_AXIS, new Point(1, 1)));
                board.addPiece(new Point(4, 3), new Fox(Direction.X_AXIS, new Point(4, 3)));

                board.addPiece(new Point(2, 4), new Mushroom(new Point(2, 4)));
                board.addPiece(new Point(3, 1), new Mushroom(new Point(3, 1)));

                setDefaultTerrain(board);
            break;
            case 2:
                board.addPiece(new Point(4, 2), new Rabbit((new Point(4, 2))));
                board.addPiece(new Point(2, 4), new Rabbit((new Point(2, 4))));

                board.addPiece(new Point(3, 1), new Fox(Direction.X_AXIS, new Point(3, 1)));

                board.addPiece(new Point(0, 1), new Mushroom(new Point(0, 1)));
                board.addPiece(new Point(1, 2), new Mushroom(new Point(1, 2)));
                board.addPiece(new Point(2, 3), new Mushroom(new Point(2, 3)));
                setDefaultTerrain(board);
            break;
            case 3:
                board.addPiece(new Point(3, 0), new Rabbit(new Point(3, 0)));
                board.addPiece(new Point(4, 2), new Rabbit(new Point(4, 2)));
                board.addPiece(new Point(1, 4), new Rabbit(new Point(1, 4)));

                board.addPiece(new Point(1, 1), new Fox(Direction.X_AXIS, new Point(1, 1)));
                board.addPiece(new Point(3, 3), new Fox(Direction.Y_AXIS, new Point(3, 3)));

                board.addPiece(new Point(3, 1), new Mushroom(new Point(3, 1)));
                board.addPiece(new Point(2, 4), new Mushroom(new Point(2, 4)));
                setDefaultTerrain(board);
            break;
            case 60:
                board.addPiece(new Point(1, 3), new Rabbit(new Point(1, 3)));
                board.addPiece(new Point(2, 4), new Rabbit(new Point(2, 4)));
                board.addPiece(new Point(4, 3), new Rabbit(new Point(4, 3)));

                board.addPiece(new Point(1, 1), new Fox(Direction.Y_AXIS, new Point(1, 1)));

                board.addPiece(new Point(0, 3), new Mushroom(new Point(0, 3)));
                board.addPiece(new Point(2, 2), new Mushroom(new Point(2, 2)));
                board.addPiece(new Point(3, 0), new Mushroom(new Point(3, 0)));
                setDefaultTerrain(board);
            break;
            default:
                setDefaultTerrain(board);

        }
        return board;
    }

    /**
     * Helper function to create the initial terrain of the board
     *
     * @param board the board to populate with terrain squares
     */

    private static void setDefaultTerrain(Board board) {
        board.addSquare(new Point(0, 0), new Square(true, true));
        board.addSquare(new Point(2, 0), new Square(false, true));
        board.addSquare(new Point(4, 0), new Square(true, true));

        board.addSquare(new Point(0, 2), new Square(false, true));
        board.addSquare(new Point(2, 2), new Square(true, true));
        board.addSquare(new Point(4, 2), new Square(false, true));

        board.addSquare(new Point(0, 4), new Square(true, true));
        board.addSquare(new Point(2, 4), new Square(false, true));
        board.addSquare(new Point(4, 4), new Square(true, true));
    }
}
