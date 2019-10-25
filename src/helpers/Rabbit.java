package helpers;

import model.Board;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rabbit implements Piece {
    @Override
    public Map<Move, List<Move>> getMoves(Board board, Point start) {
        boolean c = true;
        Map<Move, List<Move>> moves = new HashMap<>();
        Move move;
        Point point = new Point(start);
        while (c) {
            point = new Point(point.x, point.y + 1);
            c = board.hasPiece(point);
        }
        if (point.y <= board.getMax().y && !start.equals(new Point(point.x, point.y - 1))) {
            move = new Move(start, point);
            moves.put(move, List.of(move));
        }
        point = new Point(start);
        c = true;
        while (c) {
            point = new Point(point.x, point.y - 1);
            c = board.hasPiece(point);
        }
        if (point.y >= 0 && !start.equals(new Point(point.x, point.y + 1))) {
            move = new Move(start, point);
            moves.put(move, List.of(move));
        }
        point = new Point(start);
        c = true;
        while (c) {
            point = new Point(point.x + 1, point.y);
            c = board.hasPiece(point);
        }
        if (point.x <= board.getMax().x && !start.equals(new Point(point.x - 1, point.y))) {
            move = new Move(start, point);
            moves.put(move, List.of(move));
        }
        point = new Point(start);
        c = true;
        while (c) {
            point = new Point(point.x - 1, point.y);
            c = board.hasPiece(point);
        }
        if (point.x >= 0 && !start.equals(new Point(point.x + 1, point.y))) {
            move = new Move(start, point);
            moves.put(move, List.of(move));
        }
        return moves;
    }

    @Override
    public String toString() {
        return "R";
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
