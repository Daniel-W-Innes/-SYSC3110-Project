package controller;

import helpers.*;
import model.Board;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Game {
    private final Map<Integer, Level> levels;

    private Game() {
        levels = new HashMap<>();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.setUp();
    }

    public void setUp() {
        levels.put(1, genLevel(new Board.Builder(true)
                .addPieces(new Point(2, 3), new Rabbit())

                .addPieces(new Point(0, 1), new Mushroom())
                .addPieces(new Point(0, 2), new Mushroom())
                .addPieces(new Point(1, 3), new Mushroom())
                .build()
        ));
    }

    private Level genLevel(Board start) {
        Map<Board, Node.Builder> boards = new HashMap<>(genLevel(new HashMap<>(), start));
        return new Level(boards.get(start).build(), start);
    }

    private Map<Board, Node.Builder> genLevel(Map<Board, Node.Builder> boards, Board board) {
        for (Map.Entry<Point, Piece> pieces : board.getPieces().entrySet()) {
            for (Map.Entry<Move, Board> moves : pieces.getValue().getMoves(board, pieces.getKey()).entrySet()) {
                if (!boards.containsKey(board)) {
                    boards.put(board, new Node.Builder(board.getPieces().entrySet().stream()
                            .filter(entry -> entry.getValue() instanceof Rabbit)
                            .allMatch((entry -> board.hasSquare(entry.getKey()) && board.getSquare(entry.getKey()).isHole()))));
                }
                boards.put(board, boards.get(board).addEdgeBuilder(moves.getKey(), boards.containsKey(moves.getValue()) ? new Edge.Builder(boards.get(moves.getValue())) : new Edge.Builder(new Node.Builder(board.getPieces().entrySet().stream().filter(entry -> entry.getValue() instanceof Rabbit).allMatch((entry -> board.hasSquare(entry.getKey()) && board.getSquare(entry.getKey()).isHole()))))));
                if (!boards.containsKey(moves.getValue())) {
                    boards.putAll(genLevel(boards, moves.getValue()));
                }
            }
        }
        return boards;
    }
}
