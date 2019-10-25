package helpers;

import model.Board;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private final Map<Board, Map<Move, List<Move>>> graph;
    private final Map<Board, Boolean> isVictories;

    private Graph(Map<Board, Map<Move, List<Move>>> graph, Map<Board, Boolean> isVictories) {
        Map<Board, Map<Move, List<Move>>> boards = new HashMap<>();
        Map<Move, List<Move>> moves;
        for (Map.Entry<Board, Map<Move, List<Move>>> move : graph.entrySet()) {
            moves = new HashMap<>();
            for (Map.Entry<Move, List<Move>> entry : move.getValue().entrySet()) {
                moves.put(entry.getKey(), Collections.unmodifiableList(entry.getValue()));
            }
            boards.put(move.getKey(), Collections.unmodifiableMap(moves));
        }
        this.graph = Collections.unmodifiableMap(boards);
        this.isVictories = Collections.unmodifiableMap(isVictories);
    }

    boolean containsMove(Board board, Move move) {
        return graph.containsKey(board) && graph.get(board).containsKey(move);
    }

    List<Move> getMoves(Board board, Move move) {
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

    private Map<Board, Map<Move, List<Move>>> getGraph() {
        return graph;
    }

    @Override
    public int hashCode() {
        return graph.hashCode();
    }

    public static class Builder {
        private final Map<Board, Map<Move, List<Move>>> graph;
        private final Map<Board, Boolean> isVictories;

        public Builder() {
            graph = new HashMap<>();
            isVictories = new HashMap<>();
        }

        public Builder addMoves(Board board, Move move, List<Move> moves) {
            if (!graph.containsKey(board)) {
                graph.put(board, new HashMap<>());
            }
            graph.get(board).put(move, moves);
            return this;
        }

        public Builder addIsVictory(Board board, boolean isVictory) {
            isVictories.put(board, isVictory);
            return this;
        }

        public Graph build() {
            return new Graph(graph, isVictories);
        }
    }
}
