package controller;

import model.MoveCommand;
import model.Node;

import java.awt.*;

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

    String getSquareAsString(Point loc) {
        return (head.getBoard().hasSquare(loc)) ? head.getBoard().getSquare(loc).toString() : "Empty";
    }

    @Override
    public String toString() {
        return head.getBoard().toString();
    }
}
