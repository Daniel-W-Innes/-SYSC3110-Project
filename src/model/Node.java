package model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Node {

    public static class Builder {
        private final Board board;
        private final Map<MoveCommand, Builder> edges;

        public Builder(Board board) {
            this.board = board;
            edges = new HashMap<>();
        }

        public Builder addEdges(Map<MoveCommand, Builder> edges) {
            this.edges.putAll(edges);
            return this;
        }

        public Board getBoard() {
            return board;
        }

        public Node build() {
            return new Node(board, edges.entrySet().parallelStream().map(x -> Map.entry(x.getKey(), x.getValue().build())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
        }
    }

    private final Board board;
    private final Map<MoveCommand, Node> edges;

    private Node(Board board, Map<MoveCommand, Node> edges) {
        this.board = board;
        this.edges = Collections.unmodifiableMap(edges);
    }

    public Board getBoard() {
        return board;
    }

    public boolean hasNext(MoveCommand moveCommand) {
        return edges.containsKey(moveCommand);
    }

    public Node getNext(MoveCommand moveCommand) {
        return edges.get(moveCommand);
    }

}
