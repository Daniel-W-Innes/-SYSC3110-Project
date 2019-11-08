package backend.helpers;

import java.util.Arrays;

public class Move {
    private final Piece start;
    private final Piece end;
    private final int hashcode;

    Move(Piece start, Piece end) {
        this.start = start;
        this.end = end;
        hashcode = Arrays.hashCode((new int[]{start.hashCode(), end.hashCode()}));
    }

    public Piece getEnd() {
        return end;
    }

    public Piece getStart() {
        return start;
    }

    public Move getReverse() {
        return new Move(getEnd(), getStart());
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
        return getStart().equals(move.getStart()) && getEnd().equals(move.getEnd());
    }

    @Override
    public int hashCode() {
        return hashcode;
    }

    @Override
    public String toString() {
        return ""; //return "{" + getStart().x + " , " + getStart().y + "} -> {" + getEnd().x + " , " + getEnd().y + "}";
    }
}
