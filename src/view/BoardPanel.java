package view;

import controller.Game;
import helpers.Move;
import helpers.Piece;
import model.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class BoardPanel extends JPanel implements ActionListener {
    private Point clickedSquare;
    private List<Move> availableMoves;

    private final HashMap<Point, BoardTile> boardMap = new HashMap<>();
    private final Game game;

    //private static final ImageIcon emptyTileIcon = new ImageIcon("./resources/Blank.jpg");

    BoardPanel(Game game) {
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
        this.availableMoves = new ArrayList<>();
    }

    /**
     * Update the visual representation of the board terrain. Call it when you first load a level.
     * @param b the board
     */
    void updateBoardTerrain(Board b) {
        b.getTerrain().forEach((point, square) -> {
            this.boardMap.get(point).setRaised(square.isRaised());
            this.boardMap.get(point).setHole(square.isHole());
        });
    }

    void addPiece(Point point, Piece piece) {
        //this.boardMap.get(point).placePiece(piece);
        this.boardMap.get(point).setIcon(piece.getImageIcon(point));
    }

    public void removePiece(Point point) {
        this.boardMap.get(point).setIcon(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Point p = ((BoardTile) e.getSource()).getPoint();

        if(this.clickedSquare == null) {
            //1st part of the move: update currently selected square
            clickedSquare = p;
            this.availableMoves = this.game.getMoves(p);

            //Don't count the click if there are no available moves
            if(this.availableMoves.isEmpty()) {
                this.clickedSquare = null;
            } else {
                this.availableMoves.forEach(move -> {
                    //Highlight available moves
                    this.boardMap.get(move.getEndPoint()).setHighlighted(true);
                });
                System.out.println();
            }

        } else {
            //2nd part of the move: check validity and do move
            Move attemptedMove = new Move(clickedSquare.getLocation(), p);

            //Valid move
            if(this.availableMoves.contains(attemptedMove)) {
                //Delegate to controller
                this.game.movePiece(attemptedMove);
                clickedSquare = null;
                //Remove highlighting
                this.availableMoves.forEach(move -> this.boardMap.get(move.getEndPoint()).setHighlighted(false));
            } else { //Invalid move, deselect
                this.clickedSquare = null;
                //Remove highlighting
                this.availableMoves.forEach(move -> this.boardMap.get(move.getEndPoint()).setHighlighted(false));
                this.availableMoves.clear();
            }

        }
    }
}