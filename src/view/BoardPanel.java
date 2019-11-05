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
import java.util.Map;

import static model.Board.maxBoardLength;

class BoardPanel extends JPanel implements ActionListener {
    private Point clickedSquare;
    private List<Move> availableMoves;

    private final Map<Point, Tile> boardMap = new HashMap<>();
    private final Game game;

    /**
     * Initializes the panel with Tiles to represent the game
     *
     * @param game the game that needs to be represented with a GUI
     */

    BoardPanel(Game game) {
        this.game = game;
        setLayout(new GridLayout(maxBoardLength.x, maxBoardLength.y));
        //Add the BoardTiles to the board
        for (int x = 0; x < maxBoardLength.x; x++) {
            for (int y = 0; y < maxBoardLength.y; y++) {
                Point p = new Point(x, y);
                Tile tile = new Tile(p);
                tile.addActionListener(this);

                boardMap.put(p, tile);
                add(tile);
            }
        }
        availableMoves = new ArrayList<>();
    }

    /**
     * Update the visual representation of the board terrain. Call it when you first load a level.
     * @param board the new board
     */
    void updateBoardTerrain(Board board) {
        board.getTerrain().forEach((point, square) -> {
            boardMap.get(point).setRaised(square.isRaised());
            boardMap.get(point).setHole(square.isHole());
        });
    }

    /**
     * Visually add where a piece is painted to.
     *
     * @param point the point where the texture should be painted
     * @param piece the piece that is to be painted at the given point
     */

    void addPiece(Point point, Piece piece) {
        boardMap.get(point).setIcon(piece.getImageIcon(point));
    }

    /**
     * Visually remove where a piece is painted
     *
     * @param point the point where a piece should no longer be painted
     */

    void removePiece(Point point) {
        boardMap.get(point).setIcon(null);
    }

    /**
     * Handler for a button press to implement the necessary logic when the user press a Tile
     *
     * @param e the ActionEvent
     */

    @Override
    public void actionPerformed(ActionEvent e) {
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

        }
    }

    /**
     * Visually reset the board to a default state.
     */

    void reset() {
        boardMap.values().forEach(Tile::reset);
    }
}