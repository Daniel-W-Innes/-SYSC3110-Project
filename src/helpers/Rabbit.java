package helpers;

import model.Board;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Rabbit implements Piece {
    @Override
    public Map<Move, Board> getMoves(Board board, Point start) {
        boolean c = true;
        Map<Move, Board> moves = new HashMap<>();
        Board newBoard;
        Move move;
        Point point = new Point(start);
        while (c) {
            point = new Point(point.x, point.y + 1);
            c = board.hasPiece(point);
        }
        if (point.y <= board.getMax().y && !start.equals(new Point(point.x, point.y - 1))) {
            newBoard = new Board(board);
            move = new Move(start, point);
            newBoard.movePiece(move);
            moves.put(move, newBoard);
        }
        point = new Point(start);
        c = true;
        while (c) {
            point = new Point(point.x, point.y - 1);
            c = board.hasPiece(point);
        }
        if (point.y >= 0 && !start.equals(new Point(point.x, point.y + 1))) {
            newBoard = new Board(board);
            move = new Move(start, point);
            newBoard.movePiece(move);
            moves.put(move, newBoard);
        }
        point = new Point(start);
        c = true;
        while (c) {
            point = new Point(point.x + 1, point.y);
            c = board.hasPiece(point);
        }
        if (point.x <= board.getMax().x && !start.equals(new Point(point.x - 1, point.y))) {
            newBoard = new Board(board);
            move = new Move(start, point);
            newBoard.movePiece(move);
            moves.put(move, newBoard);
        }
        point = new Point(start);
        c = true;
        while (c) {
            point = new Point(point.x - 1, point.y);
            c = board.hasPiece(point);
        }
        if (point.x >= 0 && !start.equals(new Point(point.x + 1, point.y))) {
            newBoard = new Board(board);
            move = new Move(start, point);
            newBoard.movePiece(move);
            moves.put(move, newBoard);
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
