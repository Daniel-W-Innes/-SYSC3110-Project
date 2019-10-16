package model;

import java.awt.*;
import java.util.Arrays;

/**
 * Immutable class Move
 * Contains the final variables: start (Point) and end (Point), which represents the start and the end Points of a given move
 */
public final class Move {
    private final Point start;
    private final Point end;
    private final int hashcode;

    public Move(Point start, Point end){
        this.start = start;
        this.end = end;
        hashcode = Arrays.hashCode((new int[]{start.hashCode(), end.hashCode()}));
    }

    public Point getStartPoint(){
        return start;
    }

    public Point getEndPoint(){
        return end;
    }

    public boolean isPlusY() {
        return isPlusY(0);
    }

    public boolean isPlusY(int offset) {
        return start.x == end.x && start.y < end.y + offset;
    }

    public boolean isPlusX() {
        return isPlusX(0);
    }

    public boolean isPlusX(int offset) {
        return start.y == end.y && start.x < end.x + offset;
    }

    public boolean isMinusY() {
        return isMinusY(0);
    }

    public boolean isMinusY(int offset) {
        return start.x == end.x && start.y > end.y + offset;
    }

    public boolean isMinusX() {
        return isMinusX(0);
    }

    public boolean isMinusX(int offset) {
        return start.y == end.y && start.x > end.x + offset;
    }

    /**
     * Two moves are equal when startPoint and the endPoint are equivalent
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Move move = (Move) obj;
        return getStartPoint().equals(move.getStartPoint()) && getEndPoint().equals(move.getEndPoint());
    }

    @Override
    public int hashCode() {
        return hashcode;
    }

    public String toString(){
        return "Move from " + start + " to " + end;
    }
}
