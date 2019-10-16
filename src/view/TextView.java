package view;

import controller.Game;
import model.Board;
import model.Move;
import model.UserCommand;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Scanner;

/**
 * A text implementation of the GUI.
 * To run the game using the TextView, run the main method in this file
 */
public class TextView implements Observer {
    private final Game game;
    private boolean c;

    /**
     * Starts up an instance of the TextView GUI
     * @param game - The Central controller to interact with
     */
    private TextView(Game game) {
        this.game = game;
        game.setUp(this);
    }

    /**
     * Main method - Used to start the game with a TextView
     *
     * @param args - These arguments are not used
     */
    public static void main(String[] args) {
        TextView textView = new TextView(new Game());
        textView.run();
    }

    public void exit() {
        c = false;
    }

    private void run() {
        Scanner in = new Scanner(System.in);
        Method[] userCommands = Arrays.stream(game.getClass().getMethods())
                .filter(method -> method.getAnnotation(UserCommand.class) != null)
                .toArray(Method[]::new);
        c = true;

        mainLoop:
        while (c) {
            if (game.isVictory()) {
                System.out.println("YOU WON");
                c = false;
            }
            String[] inputStrings = Arrays.stream(in.nextLine().split("[\\s{},]"))
                    .filter(x -> !x.equals(""))
                    .map(String::toLowerCase)
                    .toArray(String[]::new);
            for (Method method : userCommands) {
                if (method.getName().toLowerCase().equals(inputStrings[0])) {
                    try {
                        if (method.getParameters().length == 1
                                && method.getParameters()[0].getType().equals(Move.class)
                                && method.getReturnType().equals(boolean.class)
                                && inputStrings.length == 5) {
                            if (!(boolean) method.invoke(game, new Move(new Point(Integer.parseInt(inputStrings[1]), Integer.parseInt(inputStrings[2])), new Point(Integer.parseInt(inputStrings[3]), Integer.parseInt(inputStrings[4]))))) {
                                System.out.println("Bad move");
                            }
                            continue mainLoop;
                        } else if (method.getParameters().length == 1
                                && method.getParameters()[0].getType().equals(Point.class)
                                && method.getReturnType().equals(void.class)
                                && inputStrings.length == 3) {
                            method.invoke(game, new Point(Integer.parseInt(inputStrings[1]), Integer.parseInt(inputStrings[2])));
                            continue mainLoop;
                        } else if (method.getParameters().length == 0
                                && method.getReturnType().equals(void.class)
                                && inputStrings.length == 1) {
                            method.invoke(game);
                            continue mainLoop;
                        }
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        System.out.println("Bad command");
                    }
                }
            }
            if (inputStrings[0].equals("help")) {
                if (inputStrings.length == 1) {
                    for (Method method : userCommands) {
                        System.out.printf("%s: %s\n", method.getName().toLowerCase(), method.getAnnotation(UserCommand.class).description());
                    }
                    continue;
                } else {
                    for (Method method : userCommands) {
                        for (String inputString : Arrays.copyOfRange(inputStrings, 1, inputStrings.length)) {
                            if (method.getName().toLowerCase().equals(inputString)) {
                                System.out.printf("%s: %s\n", method.getName().toLowerCase(), method.getAnnotation(UserCommand.class).description());
                            }
                        }
                    }
                    continue;
                }
            }
            System.out.println("Bad command");
        }
    }

    @Override
    public void update(Board board) {
        System.out.println(board.toString());
    }
}
