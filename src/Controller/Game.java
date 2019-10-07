package Controller;

import Model.Board;
import Model.Board.Builder;

import java.awt.*;

public class Game {
    private Board board;

    public static void main(String[] args) {
        Game game = new Game();
        game.setUp();
        System.out.println(game.board.toString());
    }

    public void setUp() {
        setUpBoard();
    }

    private void setUpBoard() {
        Point boardSize = new Point(5, 5);
        board = new Builder(boardSize)
                .addTunnel(new Point(0, 0))
                .addTunnel(new Point(4, 4))
                .addTunnel(new Point(0, 4))
                .addTunnel(new Point(4, 0))
                .addTunnel(new Point(2, 2))

                .addRaisedSquare(new Point(0, 2))
                .addRaisedSquare(new Point(2, 0))
                .addRaisedSquare(new Point(2, 4))
                .addRaisedSquare(new Point(4, 2))

                .build();
    }
}
