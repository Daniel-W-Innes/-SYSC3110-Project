package controller;

import model.*;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

class GraphBuilder {

    GraphManager getGraphManager(int levelNumber) {
        return new GraphManager(levels.get(levelNumber));
    }

    private final Map<Integer, Node> levels;

    GraphBuilder() {
        levels = new HashMap<>();
        levels.put(20, genGraph(new Board.Builder()
                .addTunnel(new ImmutablePoint(0, 0))
                .addTunnel(new ImmutablePoint(4, 4))
                .addTunnel(new ImmutablePoint(0, 4))
                .addTunnel(new ImmutablePoint(4, 0))
                .addTunnel(new ImmutablePoint(2, 2))

                .addRaisedSquare(new ImmutablePoint(0, 2))
                .addRaisedSquare(new ImmutablePoint(2, 0))
                .addRaisedSquare(new ImmutablePoint(2, 4))
                .addRaisedSquare(new ImmutablePoint(4, 2))

                .addPieces(new ImmutablePoint(1, 4), Piece.RABBIT)
                .addPieces(new ImmutablePoint(4, 2), Piece.RABBIT)
                .addPieces(new ImmutablePoint(3, 0), Piece.RABBIT)

                .addPieces(new ImmutablePoint(2, 4), Piece.MUSHROOM)
                .addPieces(new ImmutablePoint(3, 1), Piece.MUSHROOM)

                .addPieces(new ImmutablePoint(1, 1), Piece.FOX)
                .addPieces(new ImmutablePoint(1, 0), Piece.FOX)
                .addPieces(new ImmutablePoint(4, 3), Piece.FOX)
                .addPieces(new ImmutablePoint(3, 3), Piece.FOX)
                .build()));
    }

    private Node genGraph(Board start) {
        Set<Board> visitedBoards = new HashSet<>();
        Node.Builder head = new Node.Builder(start);
        Queue<Node.Builder> nodesToProcess = new ConcurrentLinkedQueue<>();
        for (Map.Entry<ImmutablePoint, Square> entry : start) {
            if (entry.getValue().hasPiece()) {
                switch (entry.getValue().getPiece()) {
                    case RABBIT:
                        Map<MoveCommand, Node.Builder> rabbitMoves = mapBoardToNode(getRabbitMoves(start, entry.getKey()));
                        head = head.addEdges(rabbitMoves);
                        nodesToProcess.addAll(rabbitMoves.values());
                }
            }
        }
        visitedBoards.add(start);
        for (Node.Builder node : nodesToProcess) {
            for (Map.Entry<ImmutablePoint, Square> entry : node.getBoard()) {
                if (entry.getValue().hasPiece()) {
                    switch (entry.getValue().getPiece()) {
                        case RABBIT:
                            Map<MoveCommand, Node.Builder> rabbitMoves = mapBoardToNode(getRabbitMoves(node.getBoard(), entry.getKey()));
                            node = node.addEdges(rabbitMoves);
                            nodesToProcess.addAll(rabbitMoves.values().parallelStream().filter(x -> visitedBoards.contains(x.getBoard())).filter(nodesToProcess::contains).collect(Collectors.toSet()));
                            visitedBoards.add(node.getBoard());
                    }
                }
            }
        }
        return head.build();
    }

    private Map<MoveCommand, Node.Builder> mapBoardToNode(Map<MoveCommand, Board> moveCommandBoardMap) {
        return moveCommandBoardMap.entrySet().parallelStream().map(x -> Map.entry(x.getKey(), new Node.Builder(x.getValue()))).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static Map<MoveCommand, Board> getRabbitMoves(Board board, ImmutablePoint start) {
        Map<MoveCommand, Board> moves = new HashMap<>();
        ImmutablePoint point = new ImmutablePoint(start.getX(), start.getY());
        boolean c = true;
        while (c) {
            point = new ImmutablePoint(point.getX(), point.getY() + 1);
            c = board.hasSquare(point) && board.getSquare(point).hasPiece();
        }
        if (point.getY() > start.getY() + 1) {
            moves.put(new MoveCommand(start, point), new Board.Copier(board).removePieces(start).addPieces(point, Piece.RABBIT).build());
        }
        point = new ImmutablePoint(start.getX(), start.getY());
        c = true;
        while (c) {
            point = new ImmutablePoint(point.getX(), point.getY() - 1);
            c = board.hasSquare(point) && board.getSquare(point).hasPiece();
        }
        if (point.getY() < start.getY() - 1) {
            moves.put(new MoveCommand(start, point), new Board.Copier(board).removePieces(start).addPieces(point, Piece.RABBIT).build());
        }
        point = new ImmutablePoint(start.getX(), start.getY());
        c = true;
        while (c) {
            point = new ImmutablePoint(point.getX() + 1, point.getY());
            c = board.hasSquare(point) && board.getSquare(point).hasPiece();
        }
        if (point.getX() > start.getX() + 1) {
            moves.put(new MoveCommand(start, point), new Board.Copier(board).removePieces(start).addPieces(point, Piece.RABBIT).build());
        }
        point = new ImmutablePoint(start.getX(), start.getY());
        c = true;
        while (c) {
            point = new ImmutablePoint(point.getX() - 1, point.getY());
            c = board.hasSquare(point) && board.getSquare(point).hasPiece();
        }
        if (point.getX() < start.getX() - 1) {
            moves.put(new MoveCommand(start, point), new Board.Copier(board).removePieces(start).addPieces(point, Piece.RABBIT).build());
        }
        return moves;
    }

    public static void main(String[] args) {
        Board board = new Board.Builder()
                .addTunnel(new ImmutablePoint(0, 0))
                .addTunnel(new ImmutablePoint(4, 4))
                .addTunnel(new ImmutablePoint(0, 4))
                .addTunnel(new ImmutablePoint(4, 0))
                .addTunnel(new ImmutablePoint(2, 2))

                .addRaisedSquare(new ImmutablePoint(0, 2))
                .addRaisedSquare(new ImmutablePoint(2, 0))
                .addRaisedSquare(new ImmutablePoint(2, 4))
                .addRaisedSquare(new ImmutablePoint(4, 2))

                .addPieces(new ImmutablePoint(1, 4), Piece.RABBIT)
                .addPieces(new ImmutablePoint(4, 2), Piece.RABBIT)
                .addPieces(new ImmutablePoint(3, 0), Piece.RABBIT)

                .addPieces(new ImmutablePoint(2, 4), Piece.MUSHROOM)
                .addPieces(new ImmutablePoint(3, 1), Piece.MUSHROOM)

                .addPieces(new ImmutablePoint(1, 1), Piece.FOX)
                .addPieces(new ImmutablePoint(1, 0), Piece.FOX)
                .addPieces(new ImmutablePoint(4, 3), Piece.FOX)
                .addPieces(new ImmutablePoint(3, 3), Piece.FOX)
                .build();
        Map<MoveCommand, Board> moves = getRabbitMoves(board, new ImmutablePoint(3, 0));
        System.out.println(board.toString());
        for (Board move : moves.values()) {
            System.out.println(move.toString());
        }
    }
}
