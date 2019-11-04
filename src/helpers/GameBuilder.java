package helpers;

import model.Board;

import java.awt.*;

public class GameBuilder {

    public static Board getStartingBoard(int levelNumber) {
        Board board = new Board();
        switch (levelNumber) {
            case 1 -> {
                board.addPiece(new Point(2, 3), new Rabbits(new Point(2, 3)));
                board.addPiece(new Point(0, 1), new Mushroom(new Point(0, 1)));
                board.addPiece(new Point(0, 2), new Mushroom(new Point(0, 2)));
                board.addPiece(new Point(1, 3), new Mushroom(new Point(1, 3)));
                setDefaultTerrain(board);
            }
            case 2 -> {
                board.addPiece(new Point(4, 2), new Rabbits((new Point(4, 2))));
                board.addPiece(new Point(2, 4), new Rabbits((new Point(2, 4))));

                board.addPiece(new Point(3, 1), new Foxes(Foxes.Direction.Y_AXIS, new Point(3, 1)));

                board.addPiece(new Point(0, 1), new Mushroom(new Point(0, 1)));
                board.addPiece(new Point(1, 2), new Mushroom(new Point(1, 2)));
                board.addPiece(new Point(2, 3), new Mushroom(new Point(2, 3)));
                setDefaultTerrain(board);
            }
            case 3 -> {
                board.addPiece(new Point(3, 0), new Rabbits(new Point(3, 0)));
                board.addPiece(new Point(4, 2), new Rabbits(new Point(4, 2)));
                board.addPiece(new Point(1, 4), new Rabbits(new Point(1, 4)));

                board.addPiece(new Point(1, 1), new Foxes(Foxes.Direction.Y_AXIS, new Point(1, 1)));
                board.addPiece(new Point(4, 3), new Foxes(Foxes.Direction.X_AXIS, new Point(4, 3)));

                board.addPiece(new Point(3, 1), new Mushroom(new Point(3, 1)));
                board.addPiece(new Point(2, 4), new Mushroom(new Point(2, 4)));
                setDefaultTerrain(board);
            }
            default -> setDefaultTerrain(board);

        }
        return board;
    }

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
