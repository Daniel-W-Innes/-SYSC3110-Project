package frontend;

import backend.helpers.Board;
import backend.helpers.Game;
import backend.helpers.Move;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class BoardPanel extends JPanel implements ActionListener {
    private Point clickedSquare;
    private List<Move> availableMoves;

    private final Map<Point, Tile> boardMap;
    private final Game game;

    /**
     * Initializes the panel with Tiles to represent the game
     *
     * @param game the game that needs to be represented with a GUI
     */

    BoardPanel(Game game) {
        this.game = game;
        availableMoves = new ArrayList<>();
        boardMap = new HashMap<>();
    }

    public void update(Board b){
        removeAll();
        availableMoves.clear();
        boardMap.clear();
        backend.helpers.Point max = b.getMax();
        setLayout(new GridLayout(max.x, max.y));
        //Add the BoardTiles to the board
        for (int y = 0; y < max.y; y++) {
            for (int x = 0; x < max.x; x++) {
                Point p = new Point(x, y);
                Tile tile = new Tile(p);
                tile.addActionListener(this);

                boardMap.put(p, tile);
                add(tile);
            }
        }
        revalidate();
        repaint();
    }

    public void update(Move m){
        System.out.print("Updated Move");
    }

    /**
     * Handler for a button press to implement the necessary logic when the user press a Tile
     *
     * @param e the ActionEvent
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        /*
        Point point = ((Tile) e.getSource()).getPoint();
        if (null == clickedSquare) {
            //1st part of the move: update currently selected square
            clickedSquare = point;
            availableMoves = game.getMoves(point);
            //Don't count the click if there are no available moves
            if (availableMoves.isEmpty()) {
                clickedSquare = null;
            } else {
                availableMoves.forEach(move -> {
                    //Highlight available moves
                    boardMap.get(move.getEndPoint()).setHighlighted(true);
                });
            }
        } else {
            //2nd part of the move: check validity and do move
            Move attemptedMove = new Move(clickedSquare.getLocation(), point);
            //Valid move
            if (availableMoves.contains(attemptedMove)) {
                //Delegate to controller
                game.movePiece(attemptedMove);
                clickedSquare = null;
                //Remove highlighting
                availableMoves.forEach(move -> boardMap.get(move.getEndPoint()).setHighlighted(false));
            } else { //Invalid move, deselect
                clickedSquare = null;
                //Remove highlighting
                availableMoves.forEach(move -> boardMap.get(move.getEndPoint()).setHighlighted(false));
                availableMoves.clear();
            }

        }*/
    }

}