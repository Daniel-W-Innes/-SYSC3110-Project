package controller;

import helpers.*;
import helpers.Fox.FoxType;
import model.Board;

import java.awt.*;
import java.util.List;
import java.util.Queue;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class Game {
    private final Map<Integer, Level> levels;
    private int levelNumber;

    private Game() {
        levels = new HashMap<>();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.setUp();
    }

    public boolean move(Move move) {
        return levels.get(levelNumber).move(move);
    }

    public List<Move> getMoves(Point point) {
        return levels.get(levelNumber).getMove(point);
    }

    public void setUp() {
        getLevels().put(1, genLevel(new Board.Builder(true)
                .addPieces(new Point(2, 3), new Rabbit(new Color(0xCD853F)))

                .addPieces(new Point(0, 1), new Mushroom())
                .addPieces(new Point(0, 2), new Mushroom())
                .addPieces(new Point(1, 3), new Mushroom())
                .build()
        ));
        getLevels().put(20, genLevel(new Board.Builder(true)
                .addPieces(new Point(1, 4), new Rabbit(new Color(0xCD853F)))
                .addPieces(new Point(4, 2), new Rabbit(new Color(0x808080)))
                .addPieces(new Point(3, 0), new Rabbit(new Color(0xFFFFFF)))

                .addPieces(new Point(2, 4), new Mushroom())
                .addPieces(new Point(3, 1), new Mushroom())

                .addPieces(new Point(1, 1), new Fox(FoxType.HEAD, Direction.PLUS_Y))
                .addPieces(new Point(1, 0), new Fox(FoxType.TAIL, Direction.MINUS_Y))
                .addPieces(new Point(4, 3), new Fox(FoxType.HEAD, Direction.PLUS_X))
                .addPieces(new Point(3, 3), new Fox(FoxType.TAIL, Direction.MINUS_X))
                .build()
        ));
        getLevels().put(60, genLevel(new Board.Builder(true)
                .addPieces(new Point(1, 3), new Rabbit(new Color(0xCD853F)))
                .addPieces(new Point(2, 4), new Rabbit(new Color(0x808080)))
                .addPieces(new Point(4, 3), new Rabbit(new Color(0xFFFFFF)))

                .addPieces(new Point(0, 3), new Mushroom())
                .addPieces(new Point(2, 2), new Mushroom())
                .addPieces(new Point(3, 0), new Mushroom())

                .addPieces(new Point(1, 1), new Fox(FoxType.HEAD, Direction.PLUS_Y))
                .addPieces(new Point(1, 0), new Fox(FoxType.TAIL, Direction.MINUS_Y))
                .build()
        ));
        levelNumber = 1;
    }

    private Level genLevel(Board start) {
        Graph.Builder graphBuilder = new Graph.Builder();
        Board newBoard;
        Set<Board> expanded = new HashSet<>();
        Queue<Board> queue = new ConcurrentLinkedQueue<>();
        queue.add(start);
        while (!queue.isEmpty()) {
            Board board = queue.poll();
            for (Map.Entry<Point, Piece> pieces : board.getPieces().entrySet()) {
                for (Map.Entry<Move, List<Move>> moves : pieces.getValue().getMoves(board, pieces.getKey()).entrySet()) {
                    graphBuilder = graphBuilder.addMoves(board, moves.getKey(), moves.getValue());
                    graphBuilder = graphBuilder.addIsVictory(board, board.isVictory());
                    newBoard = new Board(board);
                    newBoard.movePieces(moves.getValue());
                    graphBuilder = graphBuilder.addMoves(newBoard, moves.getKey().getReverse(), moves.getValue().stream().map(Move::getReverse).collect(Collectors.toList()));
                    expanded.add(board);
                    if (!expanded.contains(newBoard) && !queue.contains(newBoard)) {
                        queue.add(newBoard);
                    }
                }
            }
        }
        return new Level(graphBuilder.build(), start);
    }

    private Map<Integer, Level> getLevels() {
        return levels;
    }
}
