package helpers;

import model.Board;
import view.View;

import java.awt.*;
import java.util.List;
import java.util.Queue;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class GameBuilder {

    public static Board getStartingBoard(int levelNumber) {
        switch (levelNumber) {
            case 1 -> {
                return new Board.Builder(true)
                        .addPieces(new Point(2, 3), new Rabbit(new Color(0xCD853F)))

                        .addPieces(new Point(0, 1), new Mushroom())
                        .addPieces(new Point(0, 2), new Mushroom())
                        .addPieces(new Point(1, 3), new Mushroom())
                        .build();
            }
            case 20 -> {
                return new Board.Builder(true)
                        .addPieces(new Point(1, 4), new Rabbit(new Color(0xCD853F)))
                        .addPieces(new Point(4, 2), new Rabbit(new Color(0x808080)))
                        .addPieces(new Point(3, 0), new Rabbit(new Color(0xFFFFFF)))

                        .addPieces(new Point(2, 4), new Mushroom())
                        .addPieces(new Point(3, 1), new Mushroom())

                        .addPieces(new Point(1, 1), new Fox(Fox.FoxType.HEAD, Direction.PLUS_Y))
                        .addPieces(new Point(1, 0), new Fox(Fox.FoxType.TAIL, Direction.MINUS_Y))
                        .addPieces(new Point(4, 3), new Fox(Fox.FoxType.HEAD, Direction.PLUS_X))
                        .addPieces(new Point(3, 3), new Fox(Fox.FoxType.TAIL, Direction.MINUS_X))
                        .build();
            }
            case 60 -> {
                return new Board.Builder(true)
                        .addPieces(new Point(1, 3), new Rabbit(new Color(0xCD853F)))
                        .addPieces(new Point(2, 4), new Rabbit(new Color(0x808080)))
                        .addPieces(new Point(4, 3), new Rabbit(new Color(0xFFFFFF)))

                        .addPieces(new Point(0, 3), new Mushroom())
                        .addPieces(new Point(2, 2), new Mushroom())
                        .addPieces(new Point(3, 0), new Mushroom())

                        .addPieces(new Point(1, 1), new Fox(Fox.FoxType.HEAD, Direction.PLUS_Y))
                        .addPieces(new Point(1, 0), new Fox(Fox.FoxType.TAIL, Direction.MINUS_Y))
                        .build();
            }
            default -> {
                return new Board.Builder(true).build();
            }
        }
    }

    public static Map<Integer, Level> buildLevel(View view) {
        Map<Integer, Level> levels = new HashMap<>();
        levels.put(1, genLevel(getStartingBoard(1), view));
        levels.put(20, genLevel(getStartingBoard(20), view));
        levels.put(60, genLevel(getStartingBoard(60), view));
        return levels;
    }

    private static Level genLevel(Board start, View view) {
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
        start.addView(view);
        return new Level(graphBuilder.build(), start);
    }
}
