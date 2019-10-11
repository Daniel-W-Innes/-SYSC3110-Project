package model;

import java.util.Arrays;
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

        private Map<MoveCommand, Builder> getEdges() {
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
            Builder builder = (Builder) obj;
            return getBoard().equals(builder.getBoard()) && getEdges().equals(builder.getEdges());
        }


        @Override
        public int hashCode() {
            return Arrays.hashCode((new int[]{board.hashCode(), edges.hashCode()}));
        }
    }

    private final Board board;
    private final Map<MoveCommand, Node> edges;
    private final int hashCode;

    private Node(Board board, Map<MoveCommand, Node> edges) {
        this.board = board;
        this.edges = Collections.unmodifiableMap(edges);
        hashCode = Arrays.hashCode((new int[]{board.hashCode(), edges.hashCode()}));
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

    private Map<MoveCommand, Node> getEdges() {
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
        return getBoard().equals(node.getBoard()) && getEdges().equals(node.getEdges());
    }


    @Override
    public int hashCode() {
        return hashCode;
    }
}
