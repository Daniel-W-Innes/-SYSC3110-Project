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
    private final ImmutableNetwork<ImmutableBoard, Edge> graph;
    private final List<View> views;
    private ImmutableBoard immutableBoard;

    private Level(ImmutableNetwork<ImmutableBoard, Edge> graph, ImmutableBoard immutableBoard) {
        this.graph = graph;
        this.immutableBoard = immutableBoard;
        views = new ArrayList<>();
    }

    public boolean move(Point start, Point end) {
        for (Edge edge : graph.outEdges(immutableBoard)) {
            if (edge.getMove().getStart().occupies(start) && edge.getMove().getEnd().occupies(end)) {
                immutableBoard = edge.getEnd();
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
        return getImmutableBoard().equals(level.getImmutableBoard()) && getGraph().equals(level.getGraph());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new int[]{immutableBoard.hashCode(), graph.hashCode()});
    }

    private ImmutableNetwork<ImmutableBoard, Edge> getGraph() {
        return graph;
    }

    private ImmutableBoard getImmutableBoard() {
        return immutableBoard;
    }

    public Set<Move> getMoves(Point point) {
        return graph.outEdges(immutableBoard).stream().map(Edge::getMove).filter(move -> move.getStart().occupies(point)).collect(Collectors.toUnmodifiableSet());
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
        private final ImmutableBoard start;

        public Builder(ImmutableBoard start) {
            this.start = start;
        }

        public Level build() {
            MutableNetwork<ImmutableBoard, Edge> mutableNetwork = NetworkBuilder.directed()
                    .allowsParallelEdges(false)
                    .allowsSelfLoops(false)
                    .build();
            ImmutableBoard start;
            MutableBoard temp;
            ImmutableBoard end;
            Set<ImmutableBoard> expanded = new HashSet<>();
            Queue<ImmutableBoard> queue = new ConcurrentLinkedQueue<>();
            queue.add(this.start);
            while (!queue.isEmpty()) {
                start = queue.poll();
                for (Piece piece : start.getPieces().values()) {
                    for (Point point : piece.occupies()) {
                        for (Move move : piece.getMoves(start, point)) {
                            temp = start.getMutableBoard();
                            temp.movePiece(move);
                            end = temp.getImmutableBoard();
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
