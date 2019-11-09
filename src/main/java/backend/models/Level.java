package backend.models;

import backend.helpers.*;
import com.google.common.graph.ImmutableNetwork;
import com.google.common.graph.MutableNetwork;
import com.google.common.graph.NetworkBuilder;
import frontend.View;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class Level implements Model {
    private final ImmutableNetwork<Board, Edge> graph;
    private final List<View> views;
    private Board board;

    private Level(ImmutableNetwork<Board, Edge> graph, Board board) {
        this.graph = graph;
        this.board = board;
        views = new ArrayList<>();
    }

    public boolean move(Point start, Point end) {
        for (Edge edge : graph.outEdges(board)) {
            if (edge.getMove().getStart().occupies(start) && edge.getMove().getEnd().occupies(end)) {
                board = edge.getEnd();
                return true;
            }
        }
        return false;
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
        return board.equals(level.board) && graph.equals(level.graph);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new int[]{board.hashCode(), graph.hashCode()});
    }

    private ImmutableNetwork<Board, Edge> getGraph() {
        return graph;
    }

    private Board getBoard() {
        return board;
    }

    public Set<Move> getMoves(Point point) {
        return graph.outEdges(board).stream().map(Edge::getMove).filter(move -> move.getStart().occupies(point)).collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public void addView(View view) {
        views.add(view);
    }

    @Override
    public void removeView(View view) {
        views.remove(view);
    }

    public static class Builder {
        private final Board start;

        public Builder(Board start) {
            this.start = start;
        }

        public Level build() {
            MutableNetwork<Board, Edge> mutableNetwork = NetworkBuilder.directed()
                    .allowsParallelEdges(true)
                    .allowsSelfLoops(false)
                    .build();
            Board start;
            Board end;
            Set<Board> expanded = new HashSet<>();
            Queue<Board> queue = new ConcurrentLinkedQueue<>();
            queue.add(this.start);
            while (!queue.isEmpty()) {
                start = queue.poll();
                for (Piece piece : start.getPieces().values()) {
                    for (Point point : piece.occupies()) {
                        for (Move move : piece.getMoves(start, point)) {
                            end = new Board.Mover(start).movePiece(move).build();
                            mutableNetwork.addEdge(start, end, new Edge(move, start, end));
                            expanded.add(start);
                            if (!expanded.contains(end) && !queue.contains(end)) {
                                queue.add(end);
                            }
                        }
                    }
                }
            }
            return new Level(ImmutableNetwork.copyOf(mutableNetwork), this.start);
        }
    }
}
