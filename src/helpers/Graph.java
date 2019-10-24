package helpers;

import model.Board;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Graph {
    private final Map<Board, Map<Move, Set<Move>>> graph;

    private Graph(Map<Board, Map<Move, Set<Move>>> graph) {
        Map<Board, Map<Move, Set<Move>>> boards = new HashMap<>();
        Map<Move, Set<Move>> moves;
        for (Map.Entry<Board, Map<Move, Set<Move>>> move : graph.entrySet()) {
            moves = new HashMap<>();
            for (Map.Entry<Move, Set<Move>> entry : move.getValue().entrySet()) {
                moves.put(entry.getKey(), Collections.unmodifiableSet(entry.getValue()));
            }
            boards.put(move.getKey(), Collections.unmodifiableMap(moves));
        }
        this.graph = Collections.unmodifiableMap(boards);
    }

    boolean containsMove(Board board, Move move) {
        return graph.containsKey(board) && graph.get(board).containsKey(move);
    }

    Set<Move> getMoves(Board board, Move move) {
        return graph.get(board).get(move);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Graph graph = (Graph) obj;
        return getGraph().equals(graph.getGraph());
    }

    private Map<Board, Map<Move, Set<Move>>> getGraph() {
        return graph;
    }

    @Override
    public int hashCode() {
        return graph.hashCode();
    }

    public static class Builder {
        private final Map<Board, Map<Move, Set<Move>>> graph;

        public Builder() {
            graph = new HashMap<>();
        }

        public Builder addMoves(Board board, Move move, Set<Move> moves) {
            if (!graph.containsKey(board)) {
                graph.put(board, new HashMap<>());
            }
            graph.get(board).put(move, moves);
            return this;
        }

        public Graph build() {
            return new Graph(graph);
        }
    }
}
