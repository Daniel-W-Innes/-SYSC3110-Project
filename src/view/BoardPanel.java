package view;

import controller.Game;
import helpers.Move;
import helpers.Piece;
import helpers.Point;
import model.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The panel that holds all of the tiles for the game.
 */
class BoardPanel extends JPanel implements ActionListener {
    private final Map<Point, Tile> boardMap;
    private final Game game;
    private Point startPoint;
    private Set<Point> endPoints;

    /**
     * Initializes the panel with Tiles to represent the game
     *
     * @param game the game that needs to be represented with a GUI
     */

    BoardPanel(Game game) {
        this.game = game;
        boardMap = new HashMap<>();
        endPoints = new HashSet<>();
    }

    /**
     * Update the visual representation of the board terrain. Call it when you first load a level.
     *
     * @param board the new board
     */
    void updateBoardTerrain(Board board) {
        startPoint = null;
        removeAll();
        setLayout(new GridLayout(board.maxBoardSize.x, board.maxBoardSize.y));
        // Add the BoardTiles to the board
        for (int x = 0; x < board.maxBoardSize.x; x++) {
            for (int y = 0; y < board.maxBoardSize.y; y++) {
                Point p = new Point(x, y);
                Tile tile = new Tile(p);
                tile.addActionListener(this);
                if (board.hasSquare(p)) {
                    tile.setRaised();
                    tile.setHole(board.getSquare(p).isHole());
                }
                boardMap.put(p, tile);

                add(tile);
            }
        }
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
        Point clickedPoint = ((Tile) e.getSource()).getPoint();

        for (Map.Entry<Point, Tile> tile : boardMap.entrySet()) {
            tile.getValue().setHintPieceHighlighted(false);
            tile.getValue().setHighlighted(false);
        }

        if (null == startPoint) {
            //1st part of the move: update currently selected square
            startPoint = clickedPoint;
            endPoints = game.getEndPoint(clickedPoint);
            //Don't count the click if there are no available moves
            if (endPoints.isEmpty()) {
                startPoint = null;
            } else {
                endPoints.forEach(point -> {
                    //Highlight available moves
                    boardMap.get(point).setHighlighted(true);
                });
            }
        } else {
            //Valid move
            if (endPoints.contains(clickedPoint)) {
                //Delegate to controller
                game.movePiece(new Move(startPoint, clickedPoint));
                startPoint = null;
                //Remove highlighting
                endPoints.forEach(move -> boardMap.get(clickedPoint).setHighlighted(false));
            } else { //Invalid move, deselect
                startPoint = null;
                //Remove highlighting
                endPoints.forEach(move -> boardMap.get(clickedPoint).setHighlighted(false));
                endPoints.clear();
            }

        }
    }

    /**
     * Visually show the hint to the user without applying it first
     *
     * @param move the move representation of the hint
     */

    void showHint(Move move) {
        // Remove highlighting for all squares as the hint is showed with highlights; not removing
        // the highlight could make it confusing about what the hint actually is
        boardMap.forEach((key, value) -> {
            value.setHintPieceHighlighted(key.equals(move.getStartPoint()));
            value.setHighlighted(key.equals(move.getEndPoint()));
        });
        // Make it so that after pressing the hint, the user needs to click the a piece to move it
        startPoint = null;
    }
}