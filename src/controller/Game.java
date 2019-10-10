package controller;

import model.Command;
import model.GetCommand;
import model.MoveCommand;
import view.TextBased;
import view.ViewApi;

public class Game {
    private GraphManager graphManager;
    private final GraphBuilder graphBuilder;
    private final ViewApi view;

    public static void main(String[] args) {
        Game game = new Game(new TextBased());
        game.mainLoop();
    }

    private Game(ViewApi view) {
        graphBuilder = new GraphBuilder();
        this.view = view;
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
    }

    public void setUp() {
        graphManager = graphBuilder.getGraphManager(20);
        view.setGraphManager(graphManager);
    }
}
