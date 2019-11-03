package view;

import controller.Game;
import helpers.Move;
import helpers.Observer;
import helpers.UserCommand;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.EventObject;
import java.util.Scanner;

/**
 * A text implementation of the GUI.
 * To run the game using the TextView, run the main method in this file
 */
public class TextView implements Observer {
    private final Game game;
    private boolean keepSuffering;

    /**
     * Starts up an instance of the TextView GUI
     * @param game - The Central controller to interact with
     */
    private TextView(Game game) {
        this.game = game;
        //game.setUp(this, 1);
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

    private void run() {
        Scanner in = new Scanner(System.in);
        //Get all methods in game with the annotation UserCommand.
        Method[] userCommands = Arrays.stream(game.getClass().getMethods())
                .filter(method -> method.getAnnotation(UserCommand.class) != null)
                .toArray(Method[]::new);

        mainLoop:
        while (true) {
            //Check if the game is over
            if (game.isVictory()) {
                System.out.println("YOU WON");
                break;
            }
            System.out.println("Input a command: ");
            /*wait for user input then parse with the folding steps:
                1. Convert the input string to lowercase letters.
                2. Split the input string in to a array of string by all whitespace character, called braces, and commas.
                3. Remove all empty stings.
                4. Set inputStrings to the new array.
                e.g. "Move {3,0} {3,2}" -> "move {3,0} {3,2}" -> ["move","","3","0","","","3",2",""] -> ["move","3","0","3","2"]
             */
            String[] inputStrings = Arrays.stream(in.nextLine().toLowerCase().split("[\\s{},]"))
                    .filter(x -> !x.equals(""))
                    .toArray(String[]::new);

            for (Method method : userCommands) {
                if (method.getName().toLowerCase().equals(inputStrings[0])) {
                    try {
                        /*
                        Method invokers.
                        Each method invoker know how to invoke methods with specific attributes.
                         */
                        /*
                        Method invoker 1:
                            Number of parameters: 1
                            Parameters types: [Move]
                            Return type: boolean
                            Number of arguments from user: 5
                         */
                        if (method.getParameters().length == 1
                                && method.getParameters()[0].getType().equals(Move.class)
                                && method.getReturnType().equals(boolean.class)
                                && inputStrings.length == 5) {
                            if (!(boolean) method.invoke(game, new Move(new Point(Integer.parseInt(inputStrings[1]), Integer.parseInt(inputStrings[2])), new Point(Integer.parseInt(inputStrings[3]), Integer.parseInt(inputStrings[4]))))) {
                                System.out.println("Bad move");
                            }
                            continue mainLoop;

                            /*
                        Method invoker 2:
                            Number of parameters: 1
                            Parameters types: [Point]
                            Return type: void
                            Number of arguments from user: 3
                         */
                        } else if (method.getParameters().length == 1
                                && method.getParameters()[0].getType().equals(Point.class)
                                && method.getReturnType().equals(void.class)
                                && inputStrings.length == 3) {
                            method.invoke(game, new Point(Integer.parseInt(inputStrings[1]), Integer.parseInt(inputStrings[2])));
                            continue mainLoop;

                            /*
                        Method invoker 3:
                            Number of parameters: 1
                            Parameters types: [int]
                            Return type: void
                            Number of arguments from user: 2
                         */
                        } else if (method.getParameters().length == 1
                                && method.getParameters()[0].getType().equals(int.class)
                                && method.getReturnType().equals(void.class)
                                && inputStrings.length == 2) {
                            method.invoke(game, Integer.parseInt(inputStrings[1]));
                            continue mainLoop;

                            /*
                        Method invoker 3:
                            Number of parameters: 0
                            Parameters types: []
                            Return type: void
                            Number of arguments from user: 1
                         */
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
                // Print all method and there description.
                if (inputStrings.length == 1) {
                    for (Method method : userCommands) {
                        System.out.printf("%s: %s\n", method.getName().toLowerCase(), method.getAnnotation(UserCommand.class).description());
                    }
                    continue;
                } else {
                    // Print specified method and there description.
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
    public void update(EventObject eventObject) {
        System.out.println(eventObject.getSource().toString());
    }
}
