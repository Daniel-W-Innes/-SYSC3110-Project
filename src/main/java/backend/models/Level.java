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
import java.util.stream.Collectors;

public class Level implements Model {
    private final ImmutableNetwork<Board, Edge> graph;
    private final List<View> views;
    private Board board;
    private HashCode hashCode;

    private Level(ImmutableNetwork<Board, Edge> graph, Board board) {
        this.graph = graph;
        this.board = board;
        views = new ArrayList<>();
        genHashing();
    }

    private void genHashing() {
        hashCode = Hashing.murmur3_128().newHasher()
                .putInt(graph.hashCode())
                .putObject(board, board.getFunnel())
                .hash();
    }

    public boolean move(Point start, Point end) {
        for (Edge edge : graph.outEdges(board)) {
            if (edge.getMove().getStart().occupies(start) && edge.getMove().getEnd().occupies(end)) {
                board = edge.getEnd();
                genHashing();
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

//    Board BFS(Board start) {
//        Queue<Board> queue = new LinkedList<>();
//        Set<Board> expanded = new HashSet<>();
//        expanded.add(start);
//        Map<Board, Board> listOfStepsPerBoard = new HashMap<>();
//        queue.add(start);
//        for (Board board: Traverser.forGraph(graph).breadthFirst(start)){
//            if (board.isVictory()) {
//                return listOfStepsPerBoard.get(board);
//            } else if (listOfStepsPerBoard.containsKey(board)) {
//                listOfStepsPerBoard.put(board, newMoves);
//            }
//            for (Board : graph.get(board).values()) {
//                Board newBoard = new Board(board);
//                newBoard.movePieces(moves);
//                if (!expanded.contains(newBoard)) {
//                    expanded.add(newBoard);
//                    if (listOfStepsPerBoard.containsKey(board)) {
//                        List<Move> newMoves = new ArrayList<>(listOfStepsPerBoard.get(board));
//                        newMoves.addAll(moves);
//                        if (!isVictories.containsKey(newBoard)) {
//                            System.out.println(moves);
//                            System.out.println(newBoard.toString());
//                        }
//                        listOfStepsPerBoard.put(newBoard, newMoves);
//                    } else {
//                        listOfStepsPerBoard.put(newBoard, moves);
//                    }
//                    queue.add(newBoard);
//                }
//            }
//        }
//        return null;
//    }
}
