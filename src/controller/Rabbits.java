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
        if (move.isMinusY(1)) {
            for (int y = move.getEndPoint().y + 1; y <= move.getStartPoint().y - 1; y++) {
                point = new Point(move.getStartPoint().x, y);
                if (!board.hasSquare(point) || !board.getSquare(point).hasPiece() || point.y - 1 < 0) {
                    return;
                }
            }
            move(board, move);
        } else if (move.isPlusY(1)) {
            for (int y = move.getStartPoint().y + 1; y <= move.getEndPoint().y - 1; y++) {
                point = new Point(move.getStartPoint().x, y);
                if (!board.hasSquare(point) || !board.getSquare(point).hasPiece() || point.y + 1 > board.getMax().y) {
                    return;
                }
            }
            move(board, move);
        } else if (move.isMinusX(1)) {
            for (int x = move.getEndPoint().x + 1; x <= move.getStartPoint().x - 1; x++) {
                point = new Point(x, move.getStartPoint().y);
                if (!board.hasSquare(point) || !board.getSquare(point).hasPiece() || point.x - 1 < 0) {
                    return;
                }
            }
            move(board, move);
        } else if (move.isPlusX(1)) {
            for (int x = move.getStartPoint().x + 1; x <= move.getEndPoint().x - 1; x++) {
                point = new Point(x, move.getStartPoint().y);
                if (!board.hasSquare(point) || !board.getSquare(point).hasPiece() || point.x + 1 > board.getMax().x) {
                    return;
                }
            }
            move(board, move);
        }
    }
}
