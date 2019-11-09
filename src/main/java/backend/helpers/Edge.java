package backend.helpers;

import com.google.common.hash.Funnel;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

public final class Edge {
    private final Move move;
    private final Board start;
    private final Board end;
    private final HashCode hashCode;

    public Edge(Move move, Board start, Board end) {
        this.move = move;
        this.start = start;
        this.end = end;
        hashCode = Hashing.murmur3_128().newHasher()
                .putObject(move, move.getFunnel())
                .putObject(start, start.getFunnel())
                .putObject(end, end.getFunnel())
                .hash();
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

    public Funnel<Edge> getFunnel() {
        return (Funnel<Edge>) (from, into) -> {
            from.move.getFunnel().funnel(from.move, into);
            from.start.getFunnel().funnel(from.start, into);
            from.end.getFunnel().funnel(from.end, into);
        };
    }

    @Override
    public int hashCode() {
        return hashCode.asInt();
    }
}
