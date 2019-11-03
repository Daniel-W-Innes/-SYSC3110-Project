package helpers;

import model.Board;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Foxes {
    /**
     * Move a fox.
     *
     * @param board The board the fox on
     * @param move  The move to execute
     */
    private static void move(Board board, Move move) {
        if (board.hasSquare(move.getEndPoint())) {
            board.getSquare(move.getEndPoint()).setPiece(board.getSquare(move.getStartPoint()).getPiece());
        } else {
            board.addSquare(move.getEndPoint(), new Square(false, false, board.getSquare(move.getStartPoint()).getPiece()));
        }
        board.getSquare(move.getStartPoint()).removePiece();
        board.removeSquareIfEmpty(move.getStartPoint());
    }

    /**
     * Check if a move is valid, if so execute it.
     *
     * @param board The board to check against
     * @param move  The move to check
     * @return If the move was executed
     */
//    public static boolean checkAndMove(Board board, Move move) {
//        Point point;
//        switch (board.getSquare(move.getStartPoint()).getPiece()) {
//            case FOX_PLUS_Y:
//                if (move.isPlusY()) {
//                    //Debug: System.out.println("FOX_PLUS_Y move.isPlusY");
//                    for (int y = move.getStartPoint().y + 1; y <= move.getEndPoint().y; y++) {
//                        point = new Point(move.getStartPoint().x, y);
//                        if (board.hasSquare(point) && board.getSquare(point).hasPiece() || point.y > board.getMax().y) {
//                            return false;
//                        }
//                    }
//                    move(board, move);
//                    move(board, new Move(new Point(move.getStartPoint().x, move.getStartPoint().y - 1), new Point(move.getEndPoint().x, move.getEndPoint().y - 1)));
//                    board.notifyObserver();
//                    return true;
//                } else if (move.isMinusY()) {
//                    //Debug: System.out.println("FOX_PLUS_Y move.isMinusY");
//                    for (int y = move.getEndPoint().y - 1; y <= move.getStartPoint().y - 2; y++) {
//                        point = new Point(move.getStartPoint().x, y);
//                        if (board.hasSquare(point) && board.getSquare(point).hasPiece() || point.y < 0) {
//                            return false;
//                        }
//                    }
//                    move(board, new Move(new Point(move.getStartPoint().x, move.getStartPoint().y - 1), new Point(move.getEndPoint().x, move.getEndPoint().y - 1)));
//                    move(board, move);
//                    board.notifyObserver();
//                    return true;
//                }
//            case FOX_MINUS_Y:
//                if (move.isPlusY()) {
//                    //Debug: System.out.println("FOX_MINUS_Y move.isPlusY");
//                    for (int y = move.getStartPoint().y + 2; y <= move.getEndPoint().y + 1; y++) {
//                        point = new Point(move.getStartPoint().x, y);
//                        if (board.hasSquare(point) && board.getSquare(point).hasPiece() || point.y > board.getMax().y) {
//                            return false;
//                        }
//                    }
//                    move(board, new Move(new Point(move.getStartPoint().x, move.getStartPoint().y + 1), new Point(move.getEndPoint().x, move.getEndPoint().y + 1)));
//                    move(board, move);
//                    board.notifyObserver();
//                    return true;
//                } else if (move.isMinusY()) {
//                    //Debug: System.out.println("FOX_MINUS_Y move.isMinusY");
//                    for (int y = move.getEndPoint().y; y < move.getStartPoint().y - 1; y++) {
//                        point = new Point(move.getStartPoint().x, y);
//                        if (board.hasSquare(point) && board.getSquare(point).hasPiece() || point.y < 0) {
//                            return false;
//                        }
//                    }
//                    move(board, move);
//                    move(board, new Move(new Point(move.getStartPoint().x, move.getStartPoint().y + 1), new Point(move.getEndPoint().x, move.getEndPoint().y + 1)));
//                    board.notifyObserver();
//                    return true;
//                }
//            case FOX_PLUS_X:
//                if (move.isPlusX()) {
//                    //Debug: System.out.println("FOX_PLUS_X move.isPlusX");
//                    for (int x = move.getStartPoint().x + 1; x <= move.getEndPoint().x; x++) {
//                        point = new Point(x, move.getStartPoint().y);
//                        if (board.hasSquare(point) && board.getSquare(point).hasPiece() || point.x > board.getMax().x) {
//                            return false;
//                        }
//                    }
//                    move(board, move);
//                    move(board, new Move(new Point(move.getStartPoint().x - 1, move.getStartPoint().y), new Point(move.getEndPoint().x - 1, move.getEndPoint().y)));
//                    board.notifyObserver();
//                    return true;
//                } else if (move.isMinusX()) {
//                    //Debug: System.out.println("FOX_PLUS_X move.isMinusX");
//                    for (int x = move.getEndPoint().x - 1; x <= move.getStartPoint().x - 2; x++) {
//                        point = new Point(x, move.getStartPoint().y);
//                        if (board.hasSquare(point) && board.getSquare(point).hasPiece() || point.x < 0) {
//                            return false;
//                        }
//                    }
//                    move(board, new Move(new Point(move.getStartPoint().x - 1, move.getStartPoint().y), new Point(move.getEndPoint().x - 1, move.getEndPoint().y)));
//                    move(board, move);
//                    board.notifyObserver();
//                    return true;
//                }
//            case FOX_MINUS_X:
//                if (move.isPlusX()) {
//                    //Debug: System.out.println("FOX_MINUS_X move.isPlusX");
//                    for (int x = move.getStartPoint().x + 2; x <= move.getEndPoint().x + 1; x++) {
//                        point = new Point(x, move.getStartPoint().y);
//                        if (board.hasSquare(point) && board.getSquare(point).hasPiece() || point.x > board.getMax().x) {
//                            return false;
//                        }
//                    }
//                    move(board, new Move(new Point(move.getStartPoint().x + 1, move.getStartPoint().y), new Point(move.getEndPoint().x + 1, move.getEndPoint().y)));
//                    move(board, move);
//                    board.notifyObserver();
//                    return true;
//                } else if (move.isMinusX()) {
//                    //Debug: System.out.println("FOX_MINUS_X move.isMinusX");
//                    for (int x = move.getEndPoint().x; x <= move.getStartPoint().x - 1; x++) {
//                        point = new Point(x, move.getStartPoint().y);
//                        if (board.hasSquare(point) && board.getSquare(point).hasPiece() || point.x < 0) {
//                            return false;
//                        }
//                    }
//                    move(board, move);
//                    move(board, new Move(new Point(move.getStartPoint().x + 1, move.getStartPoint().y), new Point(move.getEndPoint().x + 1, move.getEndPoint().y)));
//                    board.notifyObserver();
//                    return true;
//                }
//        }
//        return false;
//    }

    /**
     * Get all possible moves for a fox.
     *
     * @param board The board the fox on
     * @param start The location of the fox
     * @return The possible moves
     */
    public static Map<Move, Board> getMoves(Board board, Point start) {
        boolean c = true;
        Map<Move, Board> moves = new HashMap<>();
        Board newBoard;
        Move move;
        Point point = new Point(start);
//        switch (board.getSquare(start).getPiece()) {
//            case FOX_PLUS_Y -> {
//                while (c) {
//                    if (!start.equals(point)) {
//                        newBoard = new Board(board);
//                        move = new Move(start, point);
//                        move(newBoard, move);
//                        move(newBoard, new Move(new Point(move.getStartPoint().x, move.getStartPoint().y - 1), new Point(move.getEndPoint().x, move.getEndPoint().y - 1)));
//                        moves.put(move, newBoard);
//                    }
//                    point = new Point(point.x, point.y + 1);
//                    c = !(board.hasSquare(point) && board.getSquare(point).hasPiece() || point.y > board.getMax().y);
//                }
//                return moves;
//            }
//            case FOX_MINUS_Y -> {
//                while (c) {
//                    if (!start.equals(point)) {
//                        newBoard = new Board(board);
//                        move = new Move(start, point);
//                        move(newBoard, move);
//                        move(newBoard, new Move(new Point(move.getStartPoint().x, move.getStartPoint().y + 1), new Point(move.getEndPoint().x, move.getEndPoint().y + 1)));
//                        moves.put(move, newBoard);
//                    }
//                    point = new Point(point.x, point.y - 1);
//                    c = !(board.hasSquare(point) && board.getSquare(point).hasPiece() || point.y < 0);
//                }
//                return moves;
//            }
//            case FOX_PLUS_X -> {
//                while (c) {
//                    if (!start.equals(point)) {
//                        newBoard = new Board(board);
//                        move = new Move(start, point);
//                        move(newBoard, move);
//                        move(newBoard, new Move(new Point(move.getStartPoint().x - 1, move.getStartPoint().y), new Point(move.getEndPoint().x - 1, move.getEndPoint().y)));
//                        moves.put(move, newBoard);
//                    }
//                    point = new Point(point.x + 1, point.y);
//                    c = !(board.hasSquare(point) && board.getSquare(point).hasPiece() || point.x > board.getMax().x);
//                }
//                return moves;
//            }
//            case FOX_MINUS_X -> {
//                while (c) {
//                    if (!start.equals(point)) {
//                        newBoard = new Board(board);
//                        move = new Move(start, point);
//                        move(newBoard, move);
//                        move(newBoard, new Move(new Point(move.getStartPoint().x + 1, move.getStartPoint().y), new Point(move.getEndPoint().x + 1, move.getEndPoint().y)));
//                        moves.put(move, newBoard);
//                    }
//                    point = new Point(point.x - 1, point.y);
//                    c = !(board.hasSquare(point) && board.getSquare(point).hasPiece() || point.x < 0);
//                }
//                return moves;
//            }
//        }
        return moves;
    }
}
