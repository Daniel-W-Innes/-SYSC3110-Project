package helpers;

import model.Board;

import java.util.Arrays;

public class Level {
    private final Board board;
    private final Graph graph;

    public Level(Graph graph, Board board) {
        this.graph = graph;
        this.board = board;
    }

    public boolean move(Move key) {
        if (graph.containsMove(board, key)) {
            board.movePieces(graph.getMoves(board, key));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Level level = (Level) obj;
        return getBoard() == level.getBoard() && getGraph() == level.getGraph();
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new int[]{board.hashCode(), graph.hashCode()});
    }

    private Graph getGraph() {
        return graph;
    }

    private Board getBoard() {
        return board;
    }
}
