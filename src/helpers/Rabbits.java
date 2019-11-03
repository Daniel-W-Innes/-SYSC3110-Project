package helpers;

import model.Board;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;


public class Rabbits {
    /**
     * Move a rabbit.
     *
     * @param board The board the rabbit on
     * @param move  The move to execute
     */
    private static void move(Board board, Move move) {
        board.getSquare(move.getStartPoint()).removePiece();
//        if (board.hasSquare(move.getEndPoint())) {
//            board.getSquare(move.getEndPoint()).setPiece(Piece.RABBIT);
//        } else {
//            board.addSquare(move.getEndPoint(), new Square(false, false, Piece.RABBIT));
//        }
        board.removeSquareIfEmpty(move.getStartPoint());
    }


    /**
     * Check if a move is valid, if so execute it.
     *
     * @param board The board to check against
     * @param move  The move to check
     * @return If the move was executed
     */
    public static boolean checkAndMove(Board board, Move move) {
        Point point;
        if (move.isMinusY(1)) {
            for (int y = move.getEndPoint().y + 1; y <= move.getStartPoint().y - 1; y++) {
                point = new Point(move.getStartPoint().x, y);
                if (!board.hasSquare(point) || !board.getSquare(point).hasPiece() || point.y - 1 < 0) {
                    return false;
                }
            }
            move(board, move);
            //board.notifyObserver();
            return true;
        } else if (move.isPlusY(1)) {
            for (int y = move.getStartPoint().y + 1; y <= move.getEndPoint().y - 1; y++) {
                point = new Point(move.getStartPoint().x, y);
                if (!board.hasSquare(point) || !board.getSquare(point).hasPiece() || point.y + 1 > board.getMax().y) {
                    return false;
                }
            }
            move(board, move);
            //board.notifyObserver();
            return true;
        } else if (move.isMinusX(1)) {
            for (int x = move.getEndPoint().x + 1; x <= move.getStartPoint().x - 1; x++) {
                point = new Point(x, move.getStartPoint().y);
                if (!board.hasSquare(point) || !board.getSquare(point).hasPiece() || point.x - 1 < 0) {
                    return false;
                }
            }
            move(board, move);
            //board.notifyObserver();
            return true;
        } else if (move.isPlusX(1)) {
            for (int x = move.getStartPoint().x + 1; x <= move.getEndPoint().x - 1; x++) {
                point = new Point(x, move.getStartPoint().y);
                if (!board.hasSquare(point) || !board.getSquare(point).hasPiece() || point.x + 1 > board.getMax().x) {
                    return false;
                }
            }
            move(board, move);
            //board.notifyObserver();
            return true;
        }
        return false;
    }

    /**
     * Get all possible moves for a rabbit.
     *
     * @param board The board the rabbit on
     * @param start The location of the rabbit
     * @return The possible moves
     */
    public static Map<Move, Board> getMoves(Board board, Point start) {
        boolean c = true;
        Map<Move, Board> moves = new HashMap<>();
        Board newBoard;
        Move move;
        Point point = new Point(start);
        while (c) {
            point = new Point(point.x, point.y + 1);
            c = board.hasSquare(point) && board.getSquare(point).hasPiece();
        }
        if (point.y <= board.getMax().y && !start.equals(new Point(point.x, point.y - 1))) {
            newBoard = new Board(board);
            move = new Move(start, point);
            move(newBoard, move);
            moves.put(move, newBoard);
        }
        point = new Point(start);
        c = true;
        while (c) {
            point = new Point(point.x, point.y - 1);
            c = board.hasSquare(point) && board.getSquare(point).hasPiece();
        }
        if (point.y >= 0 && !start.equals(new Point(point.x, point.y + 1))) {
            newBoard = new Board(board);
            move = new Move(start, point);
            move(newBoard, move);
            moves.put(move, newBoard);
        }
        point = new Point(start);
        c = true;
        while (c) {
            point = new Point(point.x + 1, point.y);
            c = board.hasSquare(point) && board.getSquare(point).hasPiece();
        }
        if (point.x <= board.getMax().x && !start.equals(new Point(point.x - 1, point.y))) {
            newBoard = new Board(board);
            move = new Move(start, point);
            move(newBoard, move);
            moves.put(move, newBoard);
        }
        point = new Point(start);
        c = true;
        while (c) {
            point = new Point(point.x - 1, point.y);
            c = board.hasSquare(point) && board.getSquare(point).hasPiece();
        }
        if (point.x >= 0 && !start.equals(new Point(point.x + 1, point.y))) {
            newBoard = new Board(board);
            move = new Move(start, point);
            move(newBoard, move);
            moves.put(move, newBoard);
        }
        return moves;
    }
}
