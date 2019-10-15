package view;

import controller.Game;
import model.Board;
import model.Move;

import java.awt.*;
import java.util.Arrays;
import java.util.Scanner;

public class TextView implements Observer {
    private final Game game;

    private TextView(Game game) {
        this.game = game;
    }

    public static void main(String[] args) {
        TextView textView = new TextView(new Game());
        textView.run();
    }

    private void run() {
        game.setUp(this);
        Scanner in = new Scanner(System.in);
        boolean c = true;
        while (c) {
            String[] inputStrings = Arrays.stream(in.nextLine().split("[\\s{},]")).parallel().map(String::toLowerCase).filter(x -> !x.equals("")).toArray(String[]::new);
            if (inputStrings[0].equals("move") && inputStrings.length == 5) {
                if (!game.move(new Move(new Point(Integer.parseInt(inputStrings[1]), Integer.parseInt(inputStrings[2])), new Point(Integer.parseInt(inputStrings[3]), Integer.parseInt(inputStrings[4]))))) {
                    System.out.println("Bad move");
                }
            } else if (inputStrings[0].equals("exit") && inputStrings.length == 1) {
                c = false;
            } else {
                System.out.println("Bad command");
            }
        }
    }

    public void update(Board board) {
        System.out.println(board.toString());
    }
}
