package Controller;

import Model.Command;
import Model.GetCommand;
import Model.MoveCommand;
import View.TextBased;
import View.ViewApi;

public class Game {
    private final BoardManager boardManager;
    private final ViewApi view;

    private Game(ViewApi view) {
        boardManager = new BoardManager();
        this.view = view;
        view.setBoardManager(boardManager);
    }

    public static void main(String[] args) {
        Game game = new Game(new TextBased());
        game.mainLoop();
    }

    private void mainLoop() {
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
                        MoveCommand moveCommand = (MoveCommand) command;
                        if (!(boardManager.hasPiece(moveCommand.getOldLoc()) &&
                                boardManager.getPiece(moveCommand.getOldLoc())
                                        .move(moveCommand.getOldLoc(), moveCommand.getNewLoc()))) {
                            view.drawMessage("Bad move message");
                        }
                        break;
                    case GET:
                        GetCommand getCommand = (GetCommand) command;
                        view.drawMessage(boardManager.getSquareAsString(getCommand.getLoc()));
                }
            }
        }
    }

    public void setUp() {
        boardManager.applyLevel(20);
    }
}
