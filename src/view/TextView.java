package view;

import controller.Game;
import model.Board;
import model.Move;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Scanner;

public class TextView implements Observer {
    private final Game game;
    private final String REFLECTED_PREFIX = "reflected";
    private boolean c;

    private TextView(Game game) {
        this.game = game;
    }

    public static void main(String[] args) {
        TextView textView = new TextView(new Game());
        textView.run();
    }

    public void exit() {
        c = false;
    }

    private void run() {
        game.setUp(this);
        Scanner in = new Scanner(System.in);
        Method[] gameMethods = Arrays.stream(game.getClass().getMethods())
                .filter(method -> method.getAnnotation(Game.UserCommand.class) != null)
                .toArray(Method[]::new);
        c = true;

        mainLoop:
        while (c) {
            String[] inputStrings = Arrays.stream(in.nextLine().split("[\\s{},]"))
                    .filter(x -> !x.equals(""))
                    .map(String::toLowerCase)
                    .toArray(String[]::new);
            for (Method method : gameMethods) {
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
            if (inputStrings[0].equals("help") && inputStrings.length == 1) {
                System.out.println("exit");
                for (Method method : gameMethods) {
                    System.out.printf("%s: %s\n", method.getName().toLowerCase(), method.getAnnotation(Game.UserCommand.class).description());
                }
            } else {
                System.out.println("Bad command");
            }
        }
    }

    public void update(Board board) {
        System.out.println(board.toString());
    }
}
