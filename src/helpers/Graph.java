package helpers;

import model.Board;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

/**
 * Class that generates a solution for a given board.
 */

public class Graph {

    private final Stack<Move> solution;
    private boolean isReady;
    private Thread thread;

    public Graph() {
        isReady = false;
        solution = new Stack<>();
    }

    public Graph(Proto.Graph graph, Board board) {
        solution = new Stack<>();
        if (graph.getIsReady()) {
            graph.getMoveList().forEach(move -> solution.push(new Move(move)));
        } else {
            genSolution(board);
        }
        isReady = graph.getIsReady();
    }

    public void genSolution(Board board) {
        // The solution has to be created asynchronously so that a dialog can be shown to the user that the solution is
        // being generated while the solution is being created.
        thread = new Thread(() -> {
            //Keep track of which nodes we visited
            Set<Board> visited = new HashSet<>();

            //Two queues, one for the current depth and another for the next one. This is so we can distinguish at which depth is the node at.
            Queue<TreeNode<Board>> currQueue = new ConcurrentLinkedQueue<>();
            Queue<TreeNode<Board>> nextQueue = new ConcurrentLinkedQueue<>();

            //Create a tree to track it's path
            Tree<Board> traversalPath = new Tree<>(new TreeNode<>(new Board(board)));
            currQueue.add(traversalPath.root); //Add starting root node

            outer:
            while (!currQueue.isEmpty()) {
                while (!currQueue.isEmpty()) {
                    TreeNode<Board> currNode = currQueue.poll(); //Get node to expand

                    if (currNode.contents.isVictory()) {
                        traversalPath.solution = currNode;
                        break outer;
                    }

                    //Stop traversing if you've already seen this node
                    if (visited.contains(currNode.contents)) {
                        continue;
                    } else {
                        //Mark as visited
                        visited.add(currNode.contents);
                    }

                    //For each piece on the board
                    for (Piece piece : currNode.contents.getPieces().values()) {
                        //For every point the piece occupies
                        for (Point startPoint : piece.boardSpotsUsed())
                            //For every move that the piece has
                            for (Point endPoint : piece.getEndPoint(currNode.contents, startPoint)) {

                                // Apply the move to a fresh copy of the current board to get a board representing the state
                                // of the game after the move has been applied
                                Board end = new Board(currNode.contents);

                                // The piece being passed in has to be cloned so that the updating of the position of the piece
                                // within the movePiece function does not affect the location of the equivalent piece in the current board
                                end.movePiece(piece.clonePiece(), endPoint, false);

                                //Make a new node
                                TreeNode<Board> newNode = new TreeNode<>(end);
                                newNode.move = new Move(startPoint, endPoint); //Assign it the move that caused the board state
                                //Add it to the tree
                                newNode.addParent(currNode);
                                newNode.parent = currNode;

                                //Add it to the queue to expand
                                nextQueue.add(newNode);
                            }
                    }
                }

                currQueue = nextQueue;
                nextQueue = new ConcurrentLinkedQueue<>();
            }

            // Convert the solution to a collection that allows for easy access. Since the last tree points to the
            // last hint to obtain a victory board, the best data structure is a stack for its LIFO property
            TreeNode<Board> currentNode = traversalPath.solution;

            while (currentNode != null) {
                solution.add(currentNode.move);
                currentNode = currentNode.parent;
            }

            // For some reason the last hint move is null; it is removed to prevent a null move being attempted to be used
            if (!solution.isEmpty()) {
                solution.pop();
            }

            isReady = true;
        });
        thread.start();
    }

    public Proto.Graph toProto() {
        return Proto.Graph.newBuilder()
                .setIsReady(isReady)
                .addAllMove(solution.stream().map(Move::toProto).collect(Collectors.toUnmodifiableList()))
                .build();
    }

    public Optional<Move> getHintMove() {
        return solution.empty() || !isReady || solution.empty() ? Optional.empty() : Optional.of(solution.peek());
    }

    public Thread getThread() {
        return thread;
    }

    public void advanceSolutionIndex() {
        solution.pop();
    }

    // Tree that represents the BFS traversal of the graph.
    static class Tree<E> {
        final TreeNode<E> root;
        TreeNode<E> solution;

        Tree(TreeNode<E> root) {
            this.root = root;
        }
    }

    // Class that represent a node in the graph
    static class TreeNode<E> {
        final E contents;
        Move move; //The move done for the parent to become this node
        TreeNode<E> parent; //Parent for backtracking

        TreeNode(E e) {
            this.contents = e;
        }

        void addParent(TreeNode<E> node) {
            parent = node;
        }
    }
}
