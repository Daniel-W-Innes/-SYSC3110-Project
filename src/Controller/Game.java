package Controller;

import Model.Board;
import Model.Board.Builder;
import Model.Command;
import Model.MoveCommand;
import Model.Piece;
import View.TextBased;
import View.ViewApi;

import java.awt.*;

public class Game {
    private Board board;
    private boolean exit;
    private boolean reset;
    private ViewApi view;

    private Game(ViewApi view) {
        this.view = view;
    }

    public static void main(String[] args) {
        Game game = new Game(new TextBased());
        game.mainLoop();
    }

    private void mainLoop() {
        exit = false;
        while (!exit) {
            setUp();
            reset = false;
            while (!reset) {
                view.draw(board);
                Command command = view.getCommand();
                switch (command.getCommandType()) {
                    case EXIT:
                        exit = true;
                    case RESET:
                        reset = true;
                    case MOVE:
                        MoveCommand moveCommand = (MoveCommand) command;
                        if (checkLoc(moveCommand.getOldLoc()) && checkLoc(moveCommand.getNewLoc())) {
                            Piece piece = board.getPiece(moveCommand.getOldLoc());
                            //TODO
                            switch (piece) {
                                case RABBIT:
                                    break;
                                case FOX_HEAD:
                                    break;
                                case FOX_TAIL:
                                    break;
                            }
                        }
                }
            }
        }
    }

    private boolean checkLoc(Point loc) {
        Point size = board.getSize();
        return loc.x <= size.x && loc.y <= size.y;
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
