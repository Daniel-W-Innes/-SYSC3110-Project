package backend.helpers;

import backend.models.MutableBoard;

import java.util.Set;

public abstract class Piece {
    private final MutableBoard mutableBoard;

    Piece(MutableBoard mutableBoard) {
        this.mutableBoard = mutableBoard;
    }

    abstract Set<Move> getMoves(Point point);

    abstract boolean occupies(Point point);

    public abstract Set<Point> occupies();

    MutableBoard getMutableBoard() {
        return mutableBoard;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj.getClass() == getClass();
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
