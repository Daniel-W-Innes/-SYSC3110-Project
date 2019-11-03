package helpers;

import model.Board;
import model.Model;
import view.View;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Level {
    private final Board board;
    private final Graph graph;
    private final View view;

    public Level(Graph graph, Board board, View view) {
        this.graph = graph;
        this.board = board;
        this.view = view;

        //TODO: tell the view about the initial board state
        this.board.getPieces().forEach((point, piece) -> {
            view.addPiece(point, piece);
        });

        //System.out.println(graph.BFS(board).toString());
    }

    //Called by the Game controller to change the Board model
    public boolean move(Move move) {
        if (getGraph().containsMove(getBoard(), move)) {
            this.view.addPiece(move.getEnd(), this.board.getPiece(move.getStart()));
            this.view.removePiece(move.getStart());

            getBoard().movePieces(getGraph().getMoves(getBoard(), move));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Level level = (Level) obj;
        return getBoard() == level.getBoard() && getGraph() == level.getGraph();
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new int[]{getBoard().hashCode(), getGraph().hashCode()});
    }

    public Graph getGraph() {
        return graph;
    }

    private Board getBoard() {
        return board;
    }

    public Map<Point, Square> getBoardTerrain() {
        return null; //TODO: implement
    }

    public List<Move> getMove(Point point) {
        if(this.board.hasPiece(point)) {
            return getBoard().getPiece(point).getMoves(getBoard(), point)
                    .values()
                    .stream()
                    .flatMap(List::stream)
                    .collect(Collectors.toList()); //FIXME This is crap and I need to find a better way of doing it.
        } else {
            //TODO: there is no piece... It shouldn't be a button?
            return new ArrayList<Move>();
        }
    }

//    @Override
//    public void addView(View view) {
//        this.view = view;
//    }
//
//    @Override
//    public void removeView(View view) {
//        this.views.remove(view);
//    }
}
