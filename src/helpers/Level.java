package helpers;

import model.Board;

import java.util.Arrays;

public class Level {
    private final Board board;
    private Node currentNode;

    public Level(Node currentNode, Board board) {
        this.currentNode = currentNode;
        this.board = board;
    }

    public boolean move(Move key) {
        if (currentNode.containsMove(key)) {
            Edge edge = currentNode.getEdge(key);
            for (Move move : edge.getMoves()) {
                board.movePiece(move);
            }
            currentNode = edge.getNode();
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
        return getBoard() == level.getBoard() && getCurrentNode() == level.getCurrentNode();
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new int[]{board.hashCode(), currentNode.hashCode()});
    }

    private Node getCurrentNode() {
        return currentNode;
    }

    private Board getBoard() {
        return board;
    }
}
