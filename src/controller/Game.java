package controller;

import model.Board;
import model.Piece;
import service.Command;
import service.UndoableCommand;
import view.TextView;
import view.Viewable;

import java.awt.Point;

public class Game {
    private final Viewable view;
    private Board board;

    public static void main(String[] args) {
        Game game = new Game();
    }

    private Game() {
    	this.board = new Board.Builder()
                .addTunnel(new Point(0, 0))
                .addTunnel(new Point(4, 4))
                .addTunnel(new Point(0, 4))
                .addTunnel(new Point(4, 0))
                .addTunnel(new Point(2, 2))

                .addRaisedSquare(new Point(0, 2))
                .addRaisedSquare(new Point(2, 0))
                .addRaisedSquare(new Point(2, 4))
                .addRaisedSquare(new Point(4, 2))

                .addPieces(new Point(1, 4), Piece.RABBIT)
                .addPieces(new Point(4, 2), Piece.RABBIT)
                .addPieces(new Point(3, 0), Piece.RABBIT)

                .addPieces(new Point(2, 4), Piece.MUSHROOM)
                .addPieces(new Point(3, 1), Piece.MUSHROOM)

                .addPieces(new Point(1, 1), Piece.FOX)
                .addPieces(new Point(1, 0), Piece.FOX)
                .addPieces(new Point(4, 3), Piece.FOX)
                .addPieces(new Point(3, 3), Piece.FOX)
                .build();
        this.view = new TextView(this);
        view.update(board);
    }

    public void executeCommand(Command command) {
    	board = command.execute(board);
    	if (command instanceof UndoableCommand) {
    		
    	}
    }
    
    public void undo() {
    	
    }
    /*private void mainLoop() {
        boolean exit = false;
        while (!exit) {
            setUp();
            boolean reset = false;
            while (!reset) {
                view.drawBoard();
                Command command = view.getCommand();
                switch (command.getCommandType()) {
                    case EXIT:
                        exit = true;
                        reset = true;
                        break;
                    case RESET:
                        reset = true;
                        break;
                    case MOVE:
                        if (!(graphManager.move((MoveCommand) command))) {
                            view.drawMessage("Bad move message");
                        }
                        break;
                    case GET:
                        GetCommand getCommand = (GetCommand) command;
                        view.drawMessage(graphManager.getSquareAsString(getCommand.getLoc()));
                }
            }
        }
    }*/

}
