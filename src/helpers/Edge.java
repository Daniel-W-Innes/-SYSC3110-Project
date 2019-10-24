package helpers;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Edge {
    private final Set<Move> moves;
    private final Node node;

    private Edge(Set<Move> moves, Node node) {
        this.moves = Collections.unmodifiableSet(moves);
        this.node = node;
    }

    Set<Move> getMoves() {
        return moves;
    }

    Node getNode() {
        return node;
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
        return getMoves() == edge.getMoves() && getNode() == edge.getNode();
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new int[]{moves.hashCode(), node.hashCode()});
    }

    public static class Builder {
        private final Set<Move> moves;
        private final Node.Builder nodeBuilder;

        public Builder(Node.Builder nodeBuilder) {
            this.moves = new HashSet<>();
            this.nodeBuilder = nodeBuilder;
        }

        public Builder addMove(Move move) {
            moves.add(move);
            return this;
        }

        Edge build() {
            return new Edge(moves, nodeBuilder.build());
        }
    }
}
