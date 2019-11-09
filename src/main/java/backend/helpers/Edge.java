package backend.helpers;

import java.util.Arrays;

public final class Edge {
    private final Move move;
    private final Board start;
    private final Board end;


    public Edge(Move move, Board start, Board end) {
        this.move = move;
        this.start = start;
        this.end = end;
    }

    public Board getEnd() {
        return end;
    }

    public Board getStart() {
        return start;
    }

    public Move getMove() {
        return move;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Edge edge = (Edge) obj;
        return start.equals(edge.start) && end.equals(edge.end) && move.equals(edge.move);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new int[]{move.hashCode(), start.hashCode(), end.hashCode()});
    }
}
