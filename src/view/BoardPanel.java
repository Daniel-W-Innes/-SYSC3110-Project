package view;

import controller.Game;
import helpers.Move;
import helpers.Piece;
import model.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

public class BoardPanel extends JPanel implements ActionListener {
    private Point clickedSquare;

    private HashMap<Point, BoardTile> boardMap = new HashMap<>();
    private final Game game;

    private static final ImageIcon emptyTileIcon = new ImageIcon("./resources/Blank.jpg");

    public BoardPanel(Game game) {
        this.game = game;

        this.setLayout(new GridLayout(5, 5));
        //Add the BoardTiles to the board
        for(int y = 0; y < 5; y++) {
            for(int x = 0; x < 5; x++) {
                Point p = new Point(x, y);
                BoardTile tile = new BoardTile(p);
                tile.addActionListener(this);

                this.boardMap.put(p, tile);
                this.add(tile);
            }
        }

        //TODO: remove hardcoded terrain:
        this.boardMap.get(new Point(0, 0)).setRaised(true);
        this.boardMap.get(new Point(0, 2)).setRaised(true);
        this.boardMap.get(new Point(0, 4)).setRaised(true);

        this.boardMap.get(new Point(2, 0)).setRaised(true);
        this.boardMap.get(new Point(2, 2)).setRaised(true);
        this.boardMap.get(new Point(2, 4)).setRaised(true);

        this.boardMap.get(new Point(4, 0)).setRaised(true);
        this.boardMap.get(new Point(4, 2)).setRaised(true);
        this.boardMap.get(new Point(4, 4)).setRaised(true);
    }

    /**
     * Update the visual representation of the board terrain. Call it when you first load a level.
     * @param b the board
     */
    public void updateBoardTerrain(Board b) {
        b.getBoard().forEach((point, square) -> {

        });
    }

    public void addPiece(Point point, Piece piece) {
        this.boardMap.get(point).placePiece(piece);
    }

    public void removePiece(Point point, Piece piece) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO: cancel a move
        Point p = ((BoardTile) e.getSource()).getPoint();

        if(this.clickedSquare == null) {
            //1st part of the move: update currently selected square
            clickedSquare = p;
            //TODO: this needs implementation
            List<Move> availableMoves = this.game.getMoves(p);

            //Don't count the click if there are no available moves
            if(availableMoves.isEmpty()) {
                this.clickedSquare = null;
            } else {
                //Debug print:
                System.out.println("Available moves:");
                availableMoves.forEach(move -> {
                    System.out.println(move);
                    //Highlight available moves
                    this.boardMap.get(move.getEndPoint()).setHighlighted(true);
                });
                System.out.println();
            }

        } else {
            //2nd part of the move: do the move
            //Delegate to controller
            this.game.move(new Move(clickedSquare.getLocation(), p));
            clickedSquare = null;
            //Remove highlighting
            this.boardMap.forEach((point, boardTile) -> {
                boardTile.setHighlighted(false);
            });
        }
    }
}
