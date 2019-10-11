package controller;

import model.ImmutablePoint;
import model.MoveCommand;
import model.Node;

public class GraphManager {

    private Node head;

    GraphManager(Node head) {
        this.head = head;
    }

    boolean move(MoveCommand moveCommand) {
        if (head.hasNext(moveCommand)) {
            head = head.getNext(moveCommand);
            return true;
        } else {
            return false;
        }
    }

    String getSquareAsString(ImmutablePoint loc) {
        return (head.getBoard().hasSquare(loc)) ? head.getBoard().getSquare(loc).toString() : "Empty";
    }

    @Override
    public String toString() {
        return head.getBoard().toString();
    }
}
