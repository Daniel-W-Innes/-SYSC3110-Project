package model;
import java.awt.*;
import java.util.Arrays;

public class Move {
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

    public Move getReverseMove(){
        return new Move(end, start);
    }

    public boolean isPlusY() {
        return start.x == end.x && start.y < end.y;
    }

    public boolean isPlusX() {
        return start.y == end.y && start.x < end.x;
    }

    public boolean isMinusY() {
        return start.x == end.x && start.y > end.y;
    }

    public boolean isMinusX() {
        return start.y == end.y && start.x > end.x;
    }

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
