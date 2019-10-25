package helpers;

import model.Board;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fox implements Piece {
    private final Direction direction;

    public Fox(Direction direction) {
        this.direction = direction;
    }


    private Map<Move, List<Move>> getMovesPlusY(Board board, Point start) {
        Map<Move, List<Move>> moveListMap = new HashMap<>();
        Move move;
        Point point = new Point(start);
        List<Move> moves;
        while (true) {
            point = new Point(point.x, point.y + 1);
            if (!board.hasPiece(point) && point.y < board.getMax().y) {
                moves = new ArrayList<>();
                move = new Move(start, point);
                moves.add(move);
                moves.add(new Move(new Point(start.x, start.y - 1), new Point(point.x, point.y - 1)));
                moveListMap.put(move, moves);
            } else {
                break;
            }
        }
        return moveListMap;
    }

    private Map<Move, List<Move>> getMovesPlusX(Board board, Point start) {
        Map<Move, List<Move>> moveListMap = new HashMap<>();
        Move move;
        Point point = new Point(start);
        List<Move> moves;
        while (true) {
            point = new Point(point.x + 1, point.y);
            if (!board.hasPiece(point) && point.x < board.getMax().x) {
                moves = new ArrayList<>();
                move = new Move(start, point);
                moves.add(move);
                moves.add(new Move(new Point(start.x - 1, start.y), new Point(point.x - 1, point.y)));
                moveListMap.put(move, moves);
            } else {
                break;
            }
        }
        return moveListMap;
    }

    private Map<Move, List<Move>> getMovesMinusY(Board board, Point start) {
        Map<Move, List<Move>> moveListMap = new HashMap<>();
        Move move;
        Point point = new Point(start);
        List<Move> moves;
        while (true) {
            point = new Point(point.x, point.y - 1);
            if (!board.hasPiece(point) && point.y > 0) {
                moves = new ArrayList<>();
                move = new Move(start, point);
                moves.add(move);
                moves.add(new Move(new Point(start.x, start.y + 1), new Point(point.x, point.y + 1)));
                moveListMap.put(move, moves);
            } else {
                break;
            }
        }
        return moveListMap;
    }

    private Map<Move, List<Move>> getMovesMinusX(Board board, Point start) {
        Map<Move, List<Move>> moveListMap = new HashMap<>();
        Move move;
        Point point = new Point(start);
        List<Move> moves;
        while (true) {
            point = new Point(point.x - 1, point.y);
            if (!board.hasPiece(point) && point.x > 0) {
                moves = new ArrayList<>();
                move = new Move(start, point);
                moves.add(move);
                moves.add(new Move(new Point(start.x + 1, start.y), new Point(point.x + 1, point.y)));
                moveListMap.put(move, moves);
            } else {
                break;
            }
        }
        return moveListMap;
    }

    @Override
    public Map<Move, List<Move>> getMoves(Board board, Point start) {
        Map<Move, List<Move>> moveListMap = new HashMap<>();
        switch (direction) {
            case PLUS_Y -> {
                moveListMap.putAll(getMovesPlusY(board, start));
                moveListMap.putAll(getMovesMinusY(board, new Point(start.x, start.y - 1)));
            }
            case MINUS_Y -> {
                moveListMap.putAll(getMovesPlusY(board, new Point(start.x, start.y + 1)));
                moveListMap.putAll(getMovesMinusY(board, start));
            }
            case PLUS_X -> {
                moveListMap.putAll(getMovesPlusX(board, start));
                moveListMap.putAll(getMovesMinusX(board, new Point(start.x - 1, start.y)));
            }
            case MINUS_X -> {
                moveListMap.putAll(getMovesPlusX(board, new Point(start.x + 1, start.y)));
                moveListMap.putAll(getMovesMinusX(board, start));
            }
        }
        return moveListMap;
    }

    @Override
    public String toString() {
        return "F";
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj != null && obj.getClass() == this.getClass();
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
