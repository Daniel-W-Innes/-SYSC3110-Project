package view;

import controller.Game;
import helpers.Move;
import helpers.Piece;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

//This class models the board GUI. It handles the events and communicates with the Game controller
public class Board extends JPanel implements ActionListener {
    //A move requires 2 clicks, track if a user has done the first one
    private Point clickedSquare;

    private HashMap<Point, Square> boardMap = new HashMap<>();
    private final Game game;

    //TODO: check if images exists?
    private static final ImageIcon blankTile = new ImageIcon("./resources/Blank.jpg");

    public Board(Game game) {
        this.game = game;

        setLayout(new GridLayout(5, 5));
        //Add the squares to the board
        for (int y = 0; y < 5; y++){
            for (int x = 0; x < 5; x++){
                Point p = new Point(x, y);
                Square s = new Square(p, Board.blankTile);
                s.addActionListener(this);
                boardMap.put(p, s);
                this.add(s);
            }
        }
    }

    //GameGUI delegates this method to this class?
    //I thought that this was called when a valid move was made?
    public void addPiece(Point point, Piece piece) {
        this.boardMap.get(point).setIcon(piece.getIcon());
    }
    //GameGUI delegate this method to this class?
    public void removePiece(Point point) {
        this.boardMap.get(point).setIcon(Board.blankTile);
    }

    //TODO: I don't think we need this method anymore?
    public void update(List<Move> l){}

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO: cancel a move
        Point p = ((Square) e.getSource()).getPoint();
        if (clickedSquare == null){
            //1st part of the move: update currently selected square
            clickedSquare = p;
            List<Move> availableMoves = this.game.getMoves(p);

            //TODO: remove debug printout
            System.out.println("Possible moves:");
            availableMoves.forEach(elem -> {
                System.out.println(elem);
            });
            System.out.println();

            //No available moves, don't count the thingy
            if(availableMoves.isEmpty()) {
                this.clickedSquare = null;
            }

        } else {
            //2nd part of the move: do the move

            //Let the game controller take care of the move
            this.game.move(new Move(clickedSquare.getLocation(), p));
            clickedSquare = null;
        }
    }
}
