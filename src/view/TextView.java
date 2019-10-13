package view;

import controller.Game;
import model.Board;
import model.Move;

import java.awt.*;
import java.util.Scanner;

public class TextView implements Observer {
    private final Game game;

    public TextView (Game game){
        this.game = game;
    }

    public void update(Board board) {
        System.out.println(board);
        Scanner in = new Scanner(System.in);

        //await for input
        while(!in.hasNext()){

        }

        String input = "";
        if (in.hasNext()) {
            input = in.nextLine();
        }

        //format of input: p1.x p1.y p2.x p2.y, e.g. 1 2 3 2 makes P1{x=1, y=2} and P2{x=3, y=2}
        String[] s = input.split(" ");
        Point p1 = new Point(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
        Point p2 = new Point(Integer.parseInt(s[2]), Integer.parseInt(s[3]));

        game.move(new Move(p1, p2));
    }
}
