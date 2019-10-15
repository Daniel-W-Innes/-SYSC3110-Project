package view;

import controller.Game;
import model.Board;
import model.Move;

import java.awt.Point;
import java.util.Arrays;
import java.util.Scanner;

/**
 * A text implementation of the GUI.
 * To run the game using the TextView, run the main method in this file
 */
public class TextView implements Observer {
    private final Game game;

    /**
     * Starts up an instance of the TextView GUI
     * @param game - The Central controller to interact with
     */
    private TextView(Game game) {
        this.game = game;
        game.setUp(this);

        Scanner in = new Scanner(System.in);

        while (true) {
            String[] inputStrings = Arrays.stream(in.nextLine().split("[\\s{},]")).parallel().map(String::toLowerCase).filter(x -> !x.equals("")).toArray(String[]::new);
            if (inputStrings[0].equalsIgnoreCase("move") && inputStrings.length == 5) {
                if (!game.move(new Move(new Point(Integer.parseInt(inputStrings[1]), Integer.parseInt(inputStrings[2])), new Point(Integer.parseInt(inputStrings[3]), Integer.parseInt(inputStrings[4]))))) {
                    System.out.println("Bad move");
                }
            } else if (inputStrings[0].equalsIgnoreCase("exit") && inputStrings.length == 1) {
                System.out.println("Exiting Game");
                System.exit(0);
            } else {
                System.out.println("Bad command");
            }
        }
    }

    /**
     * Main method - Used to start the game with a TextView
     * @param args - These arguments are not used
     */
    public static void main(String[] args) {
        TextView textView = new TextView(new Game());

    }

    @Override
    public void update(Board board) {
        System.out.println(board.toString());
        System.out.println("Awaiting Mmve/exit command");
    }
}
