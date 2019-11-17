package helpers;

import model.Board;

import java.awt.*;
import java.util.Queue;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Graph {

    private ArrayList<Move> solution;
    private int solutionIndex;
    private boolean isReady;

    public Graph(Board startingBoard) {
        isReady = false;
        new Thread(() -> {
            //Keep track of which nodes we visited
            Set<Board> visited = new HashSet<>();

            //Two queues, one for the current depth and another for the next one. This is so we can distinguish at which depth is the node at.
            Queue<TreeNode<Board>> currQueue = new ConcurrentLinkedQueue<>();
            Queue<TreeNode<Board>> nextQueue = new ConcurrentLinkedQueue<>();

            //Create a tree to track it's path
            Tree<Board> traversalPath = new Tree<>(new TreeNode<>(new Board(startingBoard)));
            currQueue.add(traversalPath.root); //Add starting root node

            outer:
            while (!currQueue.isEmpty()) {
                while (!currQueue.isEmpty()) {
                    TreeNode<Board> currNode = currQueue.poll(); //Get node to expand

                    if (currNode.contents.isVictory()) {
                        traversalPath.solution = currNode;
                        break outer;
                        //return new Level(ImmutableNetwork.copyOf(mutableNetwork), this.start);
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
                        for (Point point : piece.boardSpotsUsed())
                            //For every move that the piece has
                            for (Move move : piece.getMoves(currNode.contents, point)) {

                                Board end = new Board(currNode.contents);

                                end.movePiece(piece.clonePiece(), move.getEndPoint(), false);

                                //Make a new node
                                TreeNode<Board> newNode = new TreeNode<>(end);
                                newNode.move = move; //Assign it the move that caused the board state
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

            solution = new ArrayList<>();
            TreeNode<Board> currentNode = traversalPath.solution;

            while (currentNode != null) {
                solution.add(currentNode.move);
                currentNode = currentNode.parent;
            }

            solutionIndex = solution.size() - 2;
            isReady = true;
        }).start();
    }

    public Optional<Move> getHintMove() {
        return solutionIndex == -1 || !isReady ? Optional.empty() : Optional.of(solution.get(solutionIndex));
    }

    public void advanceSolutionIndex() {
        if (solutionIndex != 0 && isReady) {
            solutionIndex -= 1;
        }
    }

    //Tree that represents the BFS traversal of the graph.
    static class Tree<E> {
        final TreeNode<E> root;
        TreeNode<E> solution;

        Tree(TreeNode<E> root) {
            this.root = root;
        }
    }

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
