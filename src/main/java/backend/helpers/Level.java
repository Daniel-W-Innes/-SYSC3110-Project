package backend.helpers;

import backend.models.ImmutableBoard;
import backend.models.MutableBoard;
import com.google.common.graph.ImmutableNetwork;
import com.google.common.graph.MutableNetwork;
import com.google.common.graph.NetworkBuilder;
import frontend.View;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

class Level {
    private final MutableBoard mutableBoard;
    private final ImmutableNetwork<ImmutableBoard, Move> graph;

    private Level(ImmutableNetwork<ImmutableBoard, Move> graph, MutableBoard mutableBoard) {
        this.graph = graph;
        this.mutableBoard = mutableBoard;
        //System.out.println(graph.BFS(board).toString());
    }

    boolean move(Point start, Point end) {
        for (Move move : graph.outEdges(mutableBoard.getImmutableBoard())) {
            if (move.getStart().occupies(start) && move.getEnd().occupies(end)) {
                mutableBoard.movePiece(move);
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
        return getMutableBoard() == level.getMutableBoard() && getGraph() == level.getGraph();
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new int[]{getMutableBoard().hashCode(), getGraph().hashCode()});
    }

    private ImmutableNetwork<ImmutableBoard, Move> getGraph() {
        return graph;
    }

    private MutableBoard getMutableBoard() {
        return mutableBoard;
    }

    public void resetPieces(Map<Point, Piece> piece) {
        mutableBoard.setPieces(piece);
    }

    Set<Move> getMoves(Point point) {
        return graph.outEdges(mutableBoard.getImmutableBoard()).stream().filter(move -> move.getStart().occupies(point)).collect(Collectors.toUnmodifiableSet());
    }

    static class Builder {
        private final MutableBoard start;
        private final MutableBoard board;
        private final View view;

        Builder(MutableBoard start, View view) {
            this.start = start;
            board = new MutableBoard(start);
            this.view = view;
        }

        Level build() {
            MutableNetwork<ImmutableBoard, Move> mutableNetwork = NetworkBuilder.directed()
                    .allowsParallelEdges(false)
                    .allowsSelfLoops(false)
                    .build();
            ImmutableBoard start;
            MutableBoard temp;
            ImmutableBoard end;
            Set<ImmutableBoard> expanded = new HashSet<>();
            Queue<ImmutableBoard> queue = new ConcurrentLinkedQueue<>();
            queue.add(this.board.getImmutableBoard());
            while (!queue.isEmpty()) {
                start = queue.poll();
                for (Piece piece : start.getPieces().values()) {
                    for (Point point : piece.occupies()) {
                        for (Move move : piece.getMoves(point)) {
                            temp = start.getMutableBoard();
                            temp.movePiece(move);
                            end = temp.getImmutableBoard();
                            mutableNetwork.addEdge(start, end, move);
                            expanded.add(start);
                            if (!expanded.contains(end) && !queue.contains(end)) {
                                queue.add(end);
                            }
                        }
                    }
                }
            }
            this.start.addView(view);
            return new Level(ImmutableNetwork.copyOf(mutableNetwork), this.start);
        }
    }
}
