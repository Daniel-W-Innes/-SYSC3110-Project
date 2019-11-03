package controller;

import helpers.*;
import helpers.Fox.FoxType;
import model.Board;
import view.GameGUI;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Queue;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class Game {
    //Each "Level" contains the Board and the Graph
    private final Map<Integer, Level> levels; //The Model
    private int levelNumber;

    private final GameGUI gameGui; //The View

    private Game() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        levels = new HashMap<>();
        this.gameGui = new GameGUI(this);

        this.setUp();
    }

    public static void main(String[] args) {
        try {
            //Start the game
            Game game = new Game();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public boolean move(Move move) {
        System.out.printf("Attempting move from %s to %s\n", move.getStart(), move.getEnd());
        return levels.get(levelNumber).move(move);
    }

    public boolean changeLevel(int levelNumber) {
        if (getLevels().containsKey(levelNumber)) {
            this.levelNumber = levelNumber;
            return true;
        } else {
            return false;
        }
    }

    public List<Move> getMoves(Point point) {
        return levels.get(levelNumber).getMove(point);
    }

    /**
     * Use the Builder helper class to set up the available levels. This takes like 5 seconds to run...
     */
    public void setUp() {
        long start = System.currentTimeMillis();
        Board b1 = new Board.Builder(true)
                .addPieces(new Point(2, 3), new Rabbit(new Color(0xCD853F)))

                .addPieces(new Point(0, 1), new Mushroom())
                .addPieces(new Point(0, 2), new Mushroom())
                .addPieces(new Point(1, 3), new Mushroom())
                .build();
        //b1.addView(this.gameGui);
        Level defaultLevel = genLevel(b1);
        //Add the view to the level
        //defaultLevel.addView(this.gameGui);
        getLevels().put(1, defaultLevel);

        /*getLevels().put(20, genLevel(new Board.Builder(true)
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
        ));*/
        this.levelNumber = 1;
        System.out.println("Game.setup() takes: " + (System.currentTimeMillis()-start));
    }

    /**
     * Build and return a graph that represents the possible board states.
     * TODO: this shouldn't be here...
     * @param start the initial Board state
     * @return a Level object that contains a Graph and a Board
     */
    private Level genLevel(Board start) {
        //Build the graph that represents the game state transitions
        Graph.Builder graphBuilder = new Graph.Builder();
        Board newBoard;
        Set<Board> expanded = new HashSet<>();
        Queue<Board> queue = new ConcurrentLinkedQueue<>();
        queue.add(start);

        //Build the GraphBuilder ???
        while (!queue.isEmpty()) {
            Board board = queue.poll();
            //For every Piece
            for (Map.Entry<Point, Piece> pieces : board.getPieces().entrySet()) {
                //For every move a Piece can do
                for (Map.Entry<Move, List<Move>> moves : pieces.getValue().getMoves(board, pieces.getKey()).entrySet()) {
                    //
                    graphBuilder = graphBuilder.addMoves(board, moves.getKey(), moves.getValue());
                    graphBuilder = graphBuilder.addIsVictory(board, board.isVictory());
                    newBoard = new Board(board);
                    //TODO: Fix this. Causes problems
                    newBoard.movePieces(moves.getValue());
                    graphBuilder = graphBuilder.addMoves(newBoard, moves.getKey().getReverse(), moves.getValue().stream().map(Move::getReverse).collect(Collectors.toList()));
                    expanded.add(board);
                    if (!expanded.contains(newBoard) && !queue.contains(newBoard)) {
                        queue.add(newBoard);
                    }
                }
            }
        }
        return new Level(graphBuilder.build(), start, this.gameGui);
    }

    private Map<Integer, Level> getLevels() {
        return levels;
    }
}
