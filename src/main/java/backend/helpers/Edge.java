package backend.helpers;

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
}
