package controller;

import model.Board;
import model.Node;
import model.Piece;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

class GraphBuilder {

    GraphManager getGraphManager(int levelNumber) {
        return new GraphManager(levels.get(levelNumber));
    }

    private final Map<Integer, Node> levels;

    GraphBuilder() {
        levels = new HashMap<>();
        levels.put(20, genGraph(new Board.Builder()
                .addTunnel(new Point(0, 0))
                .addTunnel(new Point(4, 4))
                .addTunnel(new Point(0, 4))
                .addTunnel(new Point(4, 0))
                .addTunnel(new Point(2, 2))

                .addRaisedSquare(new Point(0, 2))
                .addRaisedSquare(new Point(2, 0))
                .addRaisedSquare(new Point(2, 4))
                .addRaisedSquare(new Point(4, 2))

                .addPieces(new Point(1, 4), Piece.RABBIT)
                .addPieces(new Point(4, 2), Piece.RABBIT)
                .addPieces(new Point(3, 0), Piece.RABBIT)

                .addPieces(new Point(2, 4), Piece.MUSHROOM)
                .addPieces(new Point(3, 1), Piece.MUSHROOM)

                .addPieces(new Point(1, 1), Piece.FOX)
                .addPieces(new Point(1, 0), Piece.FOX)
                .addPieces(new Point(4, 3), Piece.FOX)
                .addPieces(new Point(3, 3), Piece.FOX)
                .build()));
    }

    private Node genGraph(Board board) {
        return new Node(board);
    }
}
