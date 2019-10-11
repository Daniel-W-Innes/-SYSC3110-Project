package controller;

import model.*;

import java.awt.*;
import java.util.Queue;
import java.util.*;
import java.util.stream.Collectors;

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

    private Node genGraph(Board start) {
        Set<Node.Builder> visitedBoards = new HashSet<>();
        Node.Builder head = new Node.Builder(start);
        Queue<Node.Builder> nodesToProcess = new LinkedList<>();
        for (Map.Entry<Point, Square> entry : start) {
            if (entry.getValue().hasPiece()) {
                switch (entry.getValue().getPiece()) {
                    case RABBIT:
                        Map<MoveCommand, Node.Builder> rabbitMoves = mapBoardToNode(getRabbitMoves(start, entry.getKey()));
                        head = head.addEdges(rabbitMoves);
                        nodesToProcess.addAll(rabbitMoves.values());
                }
            }
        }
        visitedBoards.add(head);
        for (Node.Builder node : nodesToProcess) {
            nodesToProcess.remove();
            for (Map.Entry<Point, Square> entry : node.getBoard()) {
                if (entry.getValue().hasPiece()) {
                    switch (entry.getValue().getPiece()) {
                        case RABBIT:
                            Map<MoveCommand, Node.Builder> rabbitMoves = mapBoardToNode(getRabbitMoves(node.getBoard(), entry.getKey()));
                            node = node.addEdges(rabbitMoves);
                            nodesToProcess.addAll(rabbitMoves.values().parallelStream().filter(x -> !visitedBoards.contains(x)).collect(Collectors.toSet()));
                    }
                }
            }
        }
        return head.build();
    }

    private Map<MoveCommand, Node.Builder> mapBoardToNode(Map<MoveCommand, Board> moveCommandBoardMap) {
        return moveCommandBoardMap.entrySet().parallelStream().map(x -> Map.entry(x.getKey(), new Node.Builder(x.getValue()))).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<MoveCommand, Board> getRabbitMoves(Board board, Point start) {
        Map<MoveCommand, Board> moves = new HashMap<>();
        Point point = new Point(start.x, start.y + 1);
        boolean c = true;
        while (c) {
            c = board.hasSquare(point) && board.getSquare(point).hasPiece();
            point = new Point(point.x, point.y + 1);
        }
        moves.put(new MoveCommand(start, point), new Board.Copier(board).removePieces(start).addPieces(point, Piece.RABBIT).build());
        point = new Point(start.x, start.y - 1);
        c = true;
        while (c) {
            c = board.hasSquare(point) && board.getSquare(point).hasPiece();
            point = new Point(point.x, point.y - 1);
        }
        moves.put(new MoveCommand(start, point), new Board.Copier(board).removePieces(start).addPieces(point, Piece.RABBIT).build());
        point = new Point(start.x + 1, start.y);
        c = true;
        while (c) {
            c = board.hasSquare(point) && board.getSquare(point).hasPiece();
            point = new Point(point.x + 1, point.y);
        }
        moves.put(new MoveCommand(start, point), new Board.Copier(board).removePieces(start).addPieces(point, Piece.RABBIT).build());
        point = new Point(start.x - 1, start.y);
        c = true;
        while (c) {
            c = board.hasSquare(point) && board.getSquare(point).hasPiece();
            point = new Point(point.x - 1, point.y);
        }
        moves.put(new MoveCommand(start, point), new Board.Copier(board).removePieces(start).addPieces(point, Piece.RABBIT).build());
        return moves;
    }
}
