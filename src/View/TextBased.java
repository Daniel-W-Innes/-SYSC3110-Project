package View;

import Model.Board;
import Model.Command;
import Model.CommandType;
import Model.MoveCommand;

import java.awt.*;
import java.util.Arrays;
import java.util.Scanner;

public class TextBased implements ViewApi {
    @Override
    public void draw(Board board) {
        System.out.println(board.toString());
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
            }
        }
        return command;
    }
}
