package view;

import controller.Game;
import model.Board;

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

        String[] i = input.split(" ");
        Point p1 = new Point(Integer.parseInt(i[0]), Integer.parseInt(i[1]));
        Point p2 = new Point(Integer.parseInt(i[2]), Integer.parseInt(i[3]));

    }
}
