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

    private Map<Move, List<Move>> getMoves(Board board, Point start, Point offset) {
        Map<Move, List<Move>> moveListMap = new HashMap<>();
        Move move;
        Point point = new Point(start);
        List<Move> moves;
        while (true) {
            point = new Point(point.x + offset.x, point.y + offset.y);
            if (!board.hasPiece(point) && point.y < board.getMax().y && point.x < board.getMax().x && point.y > 0 && point.x > 0) {
                moves = new ArrayList<>();
                move = new Move(start, point);
                moves.add(move);
                moves.add(new Move(new Point(start.x - offset.x, start.y - offset.y), new Point(point.x - offset.x, point.y - offset.y)));
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
                moveListMap.putAll(getMoves(board, start, new Point(0, 1)));
                moveListMap.putAll(getMoves(board, new Point(start.x, start.y - 1), new Point(0, -1)));
            }
            case MINUS_Y -> {
                moveListMap.putAll(getMoves(board, new Point(start.x, start.y + 1), new Point(0, 1)));
                moveListMap.putAll(getMoves(board, start, new Point(0, -1)));
            }
            case PLUS_X -> {
                moveListMap.putAll(getMoves(board, start, new Point(1, 0)));
                moveListMap.putAll(getMoves(board, new Point(start.x - 1, start.y), new Point(-1, 0)));
            }
            case MINUS_X -> {
                moveListMap.putAll(getMoves(board, new Point(start.x + 1, start.y), new Point(1, 0)));
                moveListMap.putAll(getMoves(board, start, new Point(-1, 0)));
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
