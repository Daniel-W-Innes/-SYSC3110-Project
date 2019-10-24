package helpers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Node {
    private final Map<Move, Edge> edges;
    private final boolean isVictory;

    private Node(Map<Move, Edge> edges, boolean isVictory) {
        this.edges = Collections.unmodifiableMap(edges);
        this.isVictory = isVictory;
    }

    boolean containsMove(Move move) {
        return edges.containsKey(move);
    }

    Edge getEdge(Move move) {
        return edges.get(move);
    }

    public boolean isVictory() {
        return isVictory;
    }

    private Map<Move, Edge> getEdges() {
        return edges;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Node node = (Node) obj;
        return getEdges().equals(node.getEdges());
    }

    @Override
    public int hashCode() {
        return edges.hashCode();
    }

    public static class Builder {
        private final Map<Move, Edge.Builder> edgeBuilders;
        private final boolean isVictory;

        public Builder(boolean isVictory) {
            edgeBuilders = new HashMap<>();
            this.isVictory = isVictory;
        }

        public Builder addEdgeBuilder(Move move, Edge.Builder edgeBuilder) {
            edgeBuilders.put(move, edgeBuilder);
            return this;
        }


        public Node build() {
            return new Node(edgeBuilders.entrySet().stream().map(entry -> Map.entry(entry.getKey(), entry.getValue().build())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)), isVictory);
        }
    }
}
