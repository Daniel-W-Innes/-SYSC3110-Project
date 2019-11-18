package helpers;

import java.awt.*;
import java.util.Arrays;

/**
 * Immutable class Move represents a move from one point to anther.
 */
public class Move {
    /**
     * The start point for the move.
     */
    private final Point start;
    /**
     * The end point for the move.
     */
    private final Point end;
    /**
     * The hashcode for the move generated with {@code Arrays.hashCode()} when the move is initialized.
     */
    private final int hashcode;

    /**
     * Initialize a new move from the start to the end point.
     *
     * @param start The start point for the new move
     * @param end   The end point for the new move
     */

    public Move(Point start, Point end) {
        this.start = start;
        this.end = end;
        hashcode = Arrays.hashCode((new int[]{start.hashCode(), end.hashCode()}));
    }

    /**
     * Get the start point for the move.
     *
     * @return The start point
     */

    public Point getStartPoint() {
        return start;
    }

    /**
     * Get the end point for the move.
     *
     * @return the end point
     */

    public Point getEndPoint() {
        return end;
    }

    /**
     * Two moves are equal when startPoint and the endPoint are equal.
     *
     * @param obj The object to test against
     * @return If obj is the same as this
     */

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (null == obj || obj.getClass() != getClass()) {
            return false;
        }
        Move move = (Move) obj;
        return start.equals(move.start) && end.equals(move.end);
    }

    /**
     * Get the hashcode for the move. This hashcode is generated with {@code Arrays.hashCode()} when the move is initialized.
     *
     * @return The hashcode
     */
    @Override
    public int hashCode() {
        return hashcode;
    }

    /**
     * Get a string representation of the move. This method dose not delegate to Point's to toString because in to verbose e.g. {@code getClass().getName() + "[x=" + x + ",y=" + y + "]"}.
     *
     * @return The representative string
     */
    public String toString() {
        return "{" + start.x + ", " + start.y + "} -> {" + end.x + ", " + end.y + "}";
    }

    /**
     * Get the reverse of this move.
     *
     * @return a move object that is the reverse of this move(start and end points are swapped)
     */

    public Move getReverse() {
        return new Move(new Point(end), new Point(start));
    }

}
