package Controller;

import Model.Board;
import Model.Board.Builder;
import Model.Piece;
import View.TextBased;
import View.ViewApi;

import java.awt.*;

public class Game {
    private Board board;

    public static void main(String[] args) {
        Game game = new Game();
        ViewApi view = new TextBased();
        game.setUp();
        view.draw(game.board);
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

                .addPieces(new Point(1,4), Piece.RABBIT)
                .addPieces(new Point(4,2), Piece.RABBIT)
                .addPieces(new Point(3,0), Piece.RABBIT)

                .addPieces(new Point(2,4), Piece.MUSHROOM)
                .addPieces(new Point(3,1), Piece.MUSHROOM)

                .addPieces(new Point(1,1), Piece.FOX_HEAD)
                .addPieces(new Point(1,0), Piece.FOX_TAIL)
                .addPieces(new Point(4,3), Piece.FOX_HEAD)
                .addPieces(new Point(3,3), Piece.FOX_TAIL)
                .build();
    }
}
