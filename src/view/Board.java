package view;

import helpers.Move;
import helpers.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class Board extends JPanel implements ActionListener, View {
    private Point clickedSquare;
    private final HashMap<Point, Square> board = new HashMap<>();

    public Board(){
        setLayout(new GridLayout(5, 5));

        for (int y = 0; y < 5; y++){
            for (int x = 0; x < 5; x++){
                Point p = new Point(x, y);
                Square s = new Square(p);
                s.addActionListener(this);
                board.put(p, s);
                add(s);
            }
        }
    }

    public void set(Board b) {
        //update everything in board
    }

    private void move(Move move) {
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

    @Override
    public void addPiece(Point point, Piece piece) {

    }

    @Override
    public void removePiece(Point point) {

    }
}
