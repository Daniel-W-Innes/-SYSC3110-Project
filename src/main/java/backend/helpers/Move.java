package backend.helpers;

import com.google.common.hash.Funnel;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

public final class Move {
    private final Piece start;
    private final Piece end;
    private final HashCode hashCode;

    Move(Piece start, Piece end) {
        this.start = start;
        this.end = end;
        hashCode = Hashing.murmur3_128().newHasher()
                .putObject(start, start.getFunnel())
                .putObject(end, end.getFunnel())
                .hash();
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

    public Funnel<Move> getFunnel() {
        return (from, into) -> {
            from.start.getFunnel().funnel(from.start, into);
            from.end.getFunnel().funnel(from.end, into);
        };
    }

    @Override
    public int hashCode() {
        return hashCode.asInt();
    }

    @Override
    public String toString() {
        return start.occupies().toString() + " -> " + end.occupies().toString();
    }
}
