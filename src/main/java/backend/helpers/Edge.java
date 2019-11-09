package backend.helpers;

public class Edge {
    private final Move move;
    private final ImmutableBoard start;
    private final ImmutableBoard end;


    public Edge(Move move, ImmutableBoard start, ImmutableBoard end) {
        this.move = move;
        this.start = start;
        this.end = end;
    }

    public ImmutableBoard getEnd() {
        return end;
    }

    public ImmutableBoard getStart() {
        return start;
    }

    public Move getMove() {
        return move;
    }
}
