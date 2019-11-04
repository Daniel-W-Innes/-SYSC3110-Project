package helpers;

import model.Board;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static controller.Game.resourcesFolder;


public class Rabbit implements Piece {

    static final String imageIconLocation = resourcesFolder + File.separator + "pieces" + File.separator + "Rabbit_white.png";

    private Point boardSpot;
    private static final ImageIcon icon = new ImageIcon(imageIconLocation);

    public Rabbit(Point boardSpot) {
        this.boardSpot = boardSpot;
    }

    @Override
    public void updateBoardSpotUsed(Point newLocation) {
        boardSpot = new Point(newLocation);
    }

    private static List<Move> getMoveDirection(Board board, Point start, Point direction) {
        /*
             To find possible moves for the rabbits, search the given direction for obstacles. While
             there are obstacles, continue searching the next square in the given direction. Once no more
             obstacles are found, then the end point for a move has been found.
         */
        Point startingPointCopy = new Point(start);
        List<Move> possibleMoves = new ArrayList<>();
        boolean objectToJump = true;
        while(objectToJump){
            startingPointCopy.x += direction.x;
            startingPointCopy.y += direction.y;
            objectToJump = board.hasPiece(startingPointCopy);
        }
        // If there are no pieces in the immediate square adjacent to the rabbit in the given rabbit, there are no moves
        if(start.equals(new Point(startingPointCopy.x - direction.x, startingPointCopy.y - direction.y))) {
            return possibleMoves;
        }
        // Make sure that the possible move is within valid coordinates of the game. The '-1' seen is because
        // the coordinates star at 0, not 1.
        if (0 <= startingPointCopy.x && startingPointCopy.x <= Board.maxBoardLength.x - 1 && 0 <= startingPointCopy.y && startingPointCopy.y <= Board.maxBoardLength.y - 1) {
                possibleMoves.add(new Move(new Point(start), new Point(startingPointCopy)));
                return possibleMoves;
        }
        return possibleMoves;
    }

    @Override
    public List<Move> getMoves(Board board, Point clickedPoint) {
        List<Move> possibleMoves = new ArrayList<>();
        // Possible moves could exist in any of the four directions depending on the obstacles of the board
        // Thus all directions have to be checked.
        possibleMoves.addAll(getMoveDirection(board, boardSpot, new Point(1, 0)));
        possibleMoves.addAll(getMoveDirection(board, boardSpot, new Point(-1, 0)));
        possibleMoves.addAll(getMoveDirection(board, boardSpot, new Point(0, 1)));
        possibleMoves.addAll(getMoveDirection(board, boardSpot, new Point(0, -1)));

        return possibleMoves;
    }

    @Override
    public Set<Point> boardSpotsUsed() {
        return Set.of(boardSpot);
    }

    @Override
    public ImageIcon getImageIcon(Point location) {
        return icon;
    }
}
