package backend.models;

import backend.helpers.*;
import com.google.common.graph.ImmutableNetwork;
import com.google.common.graph.MutableNetwork;
import com.google.common.graph.NetworkBuilder;
import com.google.common.hash.Funnel;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import frontend.View;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

public class Level implements Model {
    private final ImmutableNetwork<Board, Edge> graph;
    private final List<View> views;
    private HistoryNode history;
    private final ForkJoinPool forkJoinPool;
    private Board board;
    private HashCode hashCode;

    private Level(ImmutableNetwork<Board, Edge> graph, Board board) {
        this.graph = graph;
        this.board = board;
        history = new HistoryNode(board);
        forkJoinPool = new ForkJoinPool();
        views = new ArrayList<>();
        genHashing();
        System.out.println(solve());
    }

    private void genHashing() {
        hashCode = Hashing.murmur3_128().newHasher()
                .putInt(graph.hashCode())
                .putObject(board, board.getFunnel())
                .hash();
    }

    public Optional<List<Move>> solve() {
        return forkJoinPool.invoke(new BFS(this));
    }

    public boolean move(Point start, Point end) {
        for (Edge edge : graph.outEdges(board)) {
            if (edge.getMove().getStart().occupies(start) && edge.getMove().getEnd().occupies(end)) {
                board = edge.getEnd();
                history.setNext(board);
                genHashing();
                return true;
            }
        }
        return false;
    }

    public void undo() {
        history = history.getPrevious();
        board = history.getBoard();
    }

    public void redo() {
        history = history.getNext();
        board = history.getBoard();
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

    public Funnel<Level> getFunnel() {
        return (Funnel<Level>) (from, into) -> {
            into.putInt(from.graph.hashCode());
            from.board.getFunnel().funnel(from.board, into);
        };
    }

    @Override
    public int hashCode() {
        return hashCode.asInt();
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
                    for (Move move : piece.getMoves(start)) {
                        end = new Board.Mover(start).movePiece(move).build();
                        mutableNetwork.addEdge(start, end, new Edge(move, start, end));
                        expanded.add(start);
                        if (!expanded.contains(end) && !queue.contains(end)) {
                            queue.add(end);
                        }
                    }
                }
            }
            return new Level(ImmutableNetwork.copyOf(mutableNetwork), this.start);
        }
    }


    private static class BFS extends RecursiveTask<Optional<List<Move>>> {
        private final Board board;
        private final ImmutableNetwork<Board, Edge> graph;
        private final List<Board> visited;
        private final List<Move> prerequisites;

        BFS(Level level) {
            board = level.board;
            graph = level.graph;
            visited = List.of();
            prerequisites = new ArrayList<>();
        }

        BFS(ImmutableNetwork<Board, Edge> graph, Board board, List<Board> visited, List<Move> prerequisites) {
            this.graph = graph;
            this.board = board;
            this.visited = List.copyOf(visited);
            this.prerequisites = List.copyOf(prerequisites);
        }

        @Override
        protected Optional<List<Move>> compute() {
            if (board.isVictory()) {
                return Optional.of(prerequisites);
            } else if (visited.contains(board)) {
                return Optional.empty();
            } else {
                return ForkJoinTask.invokeAll(createSubTasks())
                        .stream()
                        .map(ForkJoinTask::join)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .min(Comparator.comparingInt(List::size));
            }
        }

        private Collection<BFS> createSubTasks() {
            List<BFS> subTasks = new ArrayList<>();
            graph.outEdges(board).forEach(edge -> {
                List<Board> newVisited = new ArrayList<>(visited);
                newVisited.add(board);
                List<Move> newPrerequisites = new ArrayList<>(prerequisites);
                newPrerequisites.add(edge.getMove());
                subTasks.add(new BFS(graph, edge.getEnd(), newVisited, newPrerequisites));
            });
            return subTasks;
        }
    }
}
