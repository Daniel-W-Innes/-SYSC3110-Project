package helpers;

import model.Board;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Fox implements Piece {
    @Override
    public Map<Move, Set<Move>> getMoves(Board board, Point point) {
        return new HashMap<>();
    }

    @Override
    public String toString() {
        return "F";
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj != null && obj.getClass() == this.getClass();
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
