package controller;

import helpers.*;
import model.Board;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
        Graph.Builder graphBuilder = genLevel(new Graph.Builder(), new HashSet<>(), start);
        return new Level(graphBuilder.build(), start);
    }

    private Graph.Builder genLevel(Graph.Builder graphBuilder, Set<Board> expanded, Board board) {
        Board newBoard;
        for (Map.Entry<Point, Piece> pieces : board.getPieces().entrySet()) {
            for (Map.Entry<Move, Set<Move>> moves : pieces.getValue().getMoves(board, pieces.getKey()).entrySet()) {
                graphBuilder = graphBuilder.addMoves(board, moves.getKey(), moves.getValue());
                newBoard = new Board(board);
                newBoard.movePieces(moves.getValue());
                graphBuilder = graphBuilder.addMoves(newBoard, moves.getKey().getReverse(), moves.getValue().stream().map(Move::getReverse).collect(Collectors.toSet()));
                expanded.add(board);
                if (!expanded.contains(newBoard)) {
                    graphBuilder = genLevel(graphBuilder, expanded, newBoard);
                }
            }
        }
        return graphBuilder;
    }
}
