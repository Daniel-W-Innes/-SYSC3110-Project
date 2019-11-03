package helpers;

import model.Board;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Foxes implements Piece {

    Set<Point> occupiedBoardSpots = null;
    Point headLocation;
    Point tailLocation;
    Direction direction;

    private static ImageIcon verticalHeadIcon = new ImageIcon("./resources/Fox_down.jpg");
    private static ImageIcon verticalTailIcon = new ImageIcon("./resources/Fox_up.jpg");
    private static ImageIcon horizontalHeadIcon = new ImageIcon("./resources/Fox_right.jpg");
    private static ImageIcon horizontalTailIcon= new ImageIcon("./resources/Fox_left.jpg");

    public enum Direction
    {
        X_AXIS,
        Y_AXIS
    }

    public Foxes(Direction direction, Point headLocation) {

        /* Functionally, it does not matter the order of the fox's tail and head, as it can move both directions along its axis.
         * Because of this, and due to the images available, the tail of the fox is either:
         *
         *  > To the left of the head, if the direction of the fox is along the x-axis
         *  > To the bottom of the head (with the origin being the top-left corner. If the origin was the bottom left, then the
         *    tail would be to the top of the head).
         *
         * Due to this tail placement, if the head was along the first row or column, the index of the tail would be -1 along the respective
         * axis, which is not a valid index.
         */


        if(direction == Direction.X_AXIS && headLocation.x == 0)
        {
            throw new IllegalArgumentException("The head of the fox must not be along the first row or column!");
        }

        if(direction == Direction.Y_AXIS && headLocation.y == 0)
        {
            throw new IllegalArgumentException("The head of the fox must not be along the first row or column!");
        }

        this.direction = direction;
        this.headLocation = headLocation;

       calculateTailLocation();
    }

    @Override
    public void updateBoardSpotUsed(Point newLocation) {
        this.headLocation = new Point(newLocation);
        calculateTailLocation();
    }


    @Override
    public Set<Point> boardSpotsUsed() {
        return occupiedBoardSpots;
    }

    public Point getHeadLocation() {
        return headLocation;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public List<Move> getMoves(Board board) {

        Point headPointCopy = new Point(headLocation);
        List<Move> possibleMoves = new ArrayList<>();

        /*
            To find valid moves for the fox, a loop is done in both directions along the fox's direction.
            For example, if the fox is facing positive X, and the head is located at (3, 0), then the squares
             (4, 0) are checked for pieces and squares. While there are no obstacles, a valid move is found and stored.

            Continuing with the above example, when searching in the other direction, to the negative X, the search
            begins from the tail. Thus the squares (1, 0) and (0, 0) are checked in that order, as the tail is located
            at (2, 0). For each move that is found, however, the move must be translated so that it is relative to the head.
            Thus, if the square (1, 0) is a valid move, then the move would contain an end point of (2, 0), as this would
            result in the tail being at (1, 0) and the head at (2, 0), which was found to be clear of any pieces and squares.
         */

        switch (direction)
        {
            case X_AXIS:
                headPointCopy.x += 1;
                while(headPointCopy.x != Board.maxBoardLength) {
                    if(board.hasPiece(headPointCopy) || board.hasSquare(headPointCopy)){
                        break;
                    }
                    possibleMoves.add(new Move(new Point(headLocation), new Point(headPointCopy)));
                    headPointCopy.x += 1;
                }
                headPointCopy = new Point(tailLocation);
                headPointCopy.x -= 1;

                while(headPointCopy.x >= 0) {
                    if(board.hasPiece(headPointCopy) || board.hasSquare(headPointCopy)){
                        break;
                    }
                    // Note the translation so that the move is relative to the head
                    headPointCopy.x += 1;
                    possibleMoves.add(new Move(new Point(headLocation), new Point(headPointCopy)));
                    // To continue looping starting at the next square to the left, not only does a '-1' have to be done,
                    // but so does another '-1' to account for the translation made earlier
                    headPointCopy.x -= 2;
                }
                break;

            case Y_AXIS: // Same ideas a X_AXIS
                headPointCopy.y += 1;
                while(headPointCopy.y != Board.maxBoardLength) {
                    if(board.hasPiece(headPointCopy) || board.hasSquare(headPointCopy)){
                        break;
                    }
                    possibleMoves.add(new Move(new Point(headLocation), new Point(headPointCopy)));
                    headPointCopy.y += 1;
                }
                headPointCopy = new Point(tailLocation);
                headPointCopy.y -= 1;

                while(headPointCopy.y >= 0) {
                    if(board.hasPiece(headPointCopy) || board.hasSquare(headPointCopy)){
                        break;
                    }
                    headPointCopy.y += 1;
                    possibleMoves.add(new Move(new Point(headLocation), new Point(headPointCopy)));
                    headPointCopy.y -= 2;
                }
                break;
        }

        return possibleMoves;
    }

    @Override
    public ImageIcon getImageIcon(Point p) {
        if(this.direction == Direction.Y_AXIS) {
            if(headLocation.equals(p)) {
                return Foxes.verticalHeadIcon;
            } else if(tailLocation.equals(p)) {
                return Foxes.verticalTailIcon;
            } else {
                throw new IllegalStateException("Illegal fox state");
            }
        } else {
            if(headLocation.equals(p)) {
                return Foxes.horizontalHeadIcon;
            } else if(tailLocation.equals(p)) {
                return Foxes.horizontalTailIcon;
            } else {
                throw new IllegalStateException("Illegal fox state");
            }
        }
    }

    private void calculateTailLocation() {
        occupiedBoardSpots = new HashSet<>();

        occupiedBoardSpots.add(headLocation);

        switch(direction)
        {
            case X_AXIS:
                tailLocation = new Point(headLocation.x - 1, headLocation.y);
                break;

            case Y_AXIS:
                tailLocation = new Point(headLocation.x, headLocation.y - 1);
                break;
        }

        occupiedBoardSpots.add(tailLocation);
    }
}
