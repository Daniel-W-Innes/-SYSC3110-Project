package model;

import java.util.HashMap;
import java.util.Map;

public class Node {
    private final Board board;
    private final Map<MoveCommand, Node> edges;

    public Node(Board board) {
        this.board = board;
        edges = new HashMap<>();
    }

    void addEdges(MoveCommand moveCommand, Node node) {
        edges.put(moveCommand, node);
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
