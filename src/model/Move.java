package model;
import java.awt.*;

public class Move {
    private final Point start;
    private final Point end;
    public Move(Point start, Point end){
        this.start = start;
        this.end = end;
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

    public String toString(){
        return "Move from " + start + " to " + end;
    }
}
