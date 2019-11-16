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
        //Print out the solution
        System.out.println("Level solution: " + solve());
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

    //Tree that represents the BFS traversal of the graph.
    public static class Tree<E> {
        TreeNode<E> root;
        public TreeNode<E> solution;

        public Tree(TreeNode<E> root) {
            this.root = root;
        }
    }

    //Individual node of the tree. Each node contains a Board object and a Move object that resulted in it.
    public static class TreeNode<E> {
        public E contents;
        public Move move; //The move done for the parent to become this node

        public TreeNode<E> parent; //Parent for backtracking
        public Set<TreeNode<E>> children; //TODO: I don't need this, I only need to backtrack.

        public TreeNode(E e) {
            this.contents = e;
            this.children = new HashSet<>();
        }

        public void addNode(TreeNode<E> node) {
            this.children.add(node);
            node.parent = this;
        }
    }

    public static class Builder {
        private final Board start;

        //Create a graph with the starting board state
        public Builder(Board start) {
            this.start = start;
        }

        //Build the graph
        public Tree<Board> build() {
            MutableNetwork<Board, Edge> mutableNetwork = NetworkBuilder.directed()
                    .allowsSelfLoops(false)
                    .build();

            //Keep track of which nodes we visited
            Set<Board> visited = new HashSet<>();

            //Two queues, one for the current depth and another for the next one. This is so we can distinguish at which depth is the node at.
            Queue<TreeNode> currQueue = new ConcurrentLinkedQueue<>();
            Queue<TreeNode> nextQueue = new ConcurrentLinkedQueue<>();

            //TODO: This thing is running forever... (Frank Y.)
            int branchCount = 0;
            //Create a tree to track it's path
            Tree<Board> traversalPath = new Tree<>(new TreeNode<Board>(this.start));
            currQueue.add(traversalPath.root); //Add starting root node

            while(!currQueue.isEmpty()) {
                while (!currQueue.isEmpty()) {
                    TreeNode<Board> currNode = currQueue.poll(); //Get node to expand

                    if(currNode.contents.isVictory()) {
                        //System.out.println(start);
                        //System.out.println(start.getPieces());
                        System.out.println("Branch count: " + branchCount);
                        traversalPath.solution = currNode;
                        return traversalPath;
                        //return new Level(ImmutableNetwork.copyOf(mutableNetwork), this.start);
                    }
                    //Stop traversing if you've already seen this node
                    if(visited.contains(currNode.contents)) {
                        continue;
                    } else {
                        //Mark as visited
                        visited.add(currNode.contents);
                    }

                    //For each piece on the board
                    for (Piece piece : currNode.contents.getPieces().values()) {
                        //For every point the piece occupies
                        for (Point point : piece.occupies()) {
                            //For every move that the piece has
                            for (Move move : piece.getMoves(currNode.contents, point)) {
                                //Get a Board object that represents the result of the move
                                Board end = new Board.Mover(currNode.contents).movePiece(move).build();

                                //Add each new Board state to the next queue to process
                                //TODO: remove this
                                branchCount++;

                                //Make a new node
                                TreeNode<Board> newNode = new TreeNode<>(end);
                                newNode.move = move; //Assign it the move that caused the board state
                                //Add it to the tree
                                currNode.addNode(newNode);
                                newNode.parent = currNode;

                                //Add it to the queue to expand
                                nextQueue.add(newNode);
                            }
                        }
                    }
                }
                currQueue = nextQueue;
                nextQueue = new ConcurrentLinkedQueue<>();
            }
            throw new IllegalStateException("No solution");
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
