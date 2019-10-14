package controller;

import model.Board;
import model.Move;
import model.Piece;
import model.Square;

import java.awt.*;


class Rabbits {
    private static void move(Board board, Move move) {
        board.getSquare(move.getStartPoint()).removePiece();
        if (board.hasSquare(move.getEndPoint())) {
            board.getSquare(move.getEndPoint()).setPiece(Piece.RABBIT);
        } else {
            board.addSquare(move.getEndPoint(), new Square(false, false, Piece.RABBIT));
        }
        board.removeSquareIfEmpty(move.getStartPoint());
        board.notifyObserver();
    }

    static void checkAndMove(Board board, Move move) {
        Point point;
        if (move.getStartPoint().x == move.getEndPoint().x) {
            if (move.getStartPoint().y > move.getEndPoint().y + 1) {
                for (int y = move.getEndPoint().y + 1; y <= move.getStartPoint().y - 1; y++) {
                    point = new Point(move.getStartPoint().x, y);
                    if (!board.hasSquare(point) || !board.getSquare(point).hasPiece()) {
                        return;
                    }
                }
                move(board, move);
            } else if (move.getStartPoint().y < move.getEndPoint().y - 1) {
                for (int y = move.getStartPoint().y + 1; y <= move.getEndPoint().y - 1; y++) {
                    point = new Point(move.getStartPoint().x, y);
                    if (!board.hasSquare(point) || !board.getSquare(point).hasPiece()) {
                        return;
                    }
                }
                move(board, move);
            }
        } else if (move.getStartPoint().y == move.getEndPoint().y) {
            if (move.getStartPoint().x > move.getEndPoint().x + 1) {
                for (int x = move.getEndPoint().x + 1; x <= move.getStartPoint().x - 1; x++) {
                    point = new Point(x, move.getStartPoint().y);
                    if (!board.hasSquare(point) || !board.getSquare(point).hasPiece()) {
                        return;
                    }
                }
                move(board, move);
            } else if (move.getStartPoint().x < move.getEndPoint().x - 1) {
                for (int x = move.getStartPoint().x + 1; x <= move.getEndPoint().x - 1; x++) {
                    point = new Point(x, move.getStartPoint().y);
                    if (!board.hasSquare(point) || !board.getSquare(point).hasPiece()) {
                        return;
                    }
                }
                move(board, move);
            }
        }
    }
}
