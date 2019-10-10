package view;

import controller.GraphManager;
import model.Command;
import model.CommandType;
import model.GetCommand;
import model.MoveCommand;

import java.awt.*;
import java.util.Arrays;
import java.util.Scanner;

public class TextBased implements ViewApi {
    private GraphManager graphManager;
    @Override
    public void setGraphManager(GraphManager graphManager) {
        this.graphManager = graphManager;
    }

    @Override
    public void drawBoard() {
        System.out.println(graphManager.toString());
    }

    @Override
    public Command getCommand() {
        Command command = null;
        while (command == null) {
            Scanner in = new Scanner(System.in);
            String[] inputStrings = Arrays.stream(in.nextLine().split(" ")).parallel().map(String::toLowerCase).toArray(String[]::new);
            if (CommandType.EXIT.toString().equals(inputStrings[0])) {
                command = new Command(CommandType.EXIT);
            } else if (CommandType.MOVE.toString().equals(inputStrings[0])) {
                if (inputStrings.length == 5) {
                    command = new MoveCommand(CommandType.MOVE, new Point(Integer.parseInt(inputStrings[1]), Integer.parseInt(inputStrings[2])), new Point(Integer.parseInt(inputStrings[3]), Integer.parseInt(inputStrings[4])));
                }
            } else if (CommandType.RESET.toString().equals(inputStrings[0])) {
                command = new Command(CommandType.RESET);
            } else if (CommandType.GET.toString().equals(inputStrings[0])) {
                if (inputStrings.length == 3) {
                    command = new GetCommand(CommandType.GET, new Point(Integer.parseInt(inputStrings[1]), Integer.parseInt(inputStrings[2])));
                }
            }
        }
        return command;
    }

    @Override
    public void drawMessage(String message) {
        System.out.println(message);
    }
}
