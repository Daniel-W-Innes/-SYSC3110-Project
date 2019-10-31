package view;

import helpers.Move;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Board extends JPanel implements ActionListener {
    private Point clickedSquare;
    private HashMap<Point, Square> b = new HashMap<>();

    public Board(){
        setLayout(new GridLayout(5, 5));

        for (int y = 0; y < 5; y++){
            for (int x = 0; x < 5; x++){
                Point p = new Point(x, y);
                Square s = new Square(p);
                s.addActionListener(this);
                b.put(p, s);
                add(s);
            }
        }
    }

    public void update(Board b) {
        //update everything in board
    }

    public void update(List<Move> l){

    }

    public void move(Move move){
        System.out.println("Moved from " + move);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Point p = ((Square) e.getSource()).getPoint();
        if (clickedSquare == null){
            clickedSquare = p;
        } else {
            move(new Move(clickedSquare, p));
            clickedSquare = null;
        }
    }
}
