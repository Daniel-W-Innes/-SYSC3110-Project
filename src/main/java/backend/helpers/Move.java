package backend.helpers;

import java.util.Arrays;

public final class Move {
    private final Piece start;
    private final Piece end;

    Move(Piece start, Piece end) {
        this.start = start;
        this.end = end;
    }

    public Piece getEnd() {
        return end;
    }

    public Piece getStart() {
        return start;
    }

    public Move getReverse() {
        return new Move(end, start);
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
        return start.equals(move.start) && end.equals(move.end);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode((new int[]{start.hashCode(), end.hashCode()}));
    }

    @Override
    public String toString() {
        return start.occupies().toString() + " -> " + end.occupies().toString();
    }
}
