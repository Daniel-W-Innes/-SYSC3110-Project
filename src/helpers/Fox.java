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


    @Override
    public Map<Move, List<Move>> getMoves(Board board, Point start) {
        Map<Move, List<Move>> moveListMap = new HashMap<>();
        Move move;
        Point point = new Point(start);
        List<Move> moves;
        switch (direction) {
            case PLUS_Y -> {
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
            }
            case MINUS_Y -> {
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
            }
            case PLUS_X -> {
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
            }
            case MINUS_X -> {
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
