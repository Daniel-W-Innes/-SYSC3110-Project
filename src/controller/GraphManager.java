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

    private Node getHead() {
        return head;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        GraphManager graphManager = (GraphManager) obj;
        return getHead() == graphManager.getHead();
    }

    @Override
    public int hashCode() {
        return getHead().hashCode();
    }

    @Override
    public String toString() {
        return head.getBoard().toString();
    }
}
