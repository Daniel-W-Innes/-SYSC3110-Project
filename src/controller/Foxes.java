package controller;

import model.Board;
import model.Move;

import java.awt.*;

class Foxes {
    static boolean checkAndMove(Board board, Move move) {
        Point point;
        switch (board.getSquare(move.getStartPoint()).getPiece()) {
            case FOX_PLUS_Y:
            case FOX_MINUS_Y:
                if (move.getStartPoint().y > move.getStartPoint().y) {
                    for (int y = move.getEndPoint().y; y < move.getStartPoint().y - 1; y++) {
                        point = new Point(move.getStartPoint().x, y);
                        if (board.hasSquare(point) && board.getSquare(point).hasPiece()) {
                            return false;
                        }
                    }
                    return true;
                } else if (move.getStartPoint().y < move.getEndPoint().y - 1) {
                    for (int y = move.getStartPoint().y + 1; y < move.getEndPoint().y; y++) {
                        point = new Point(move.getStartPoint().x, y);
                        if (board.hasSquare(point) && board.getSquare(point).hasPiece()) {
                            return false;
                        }
                    }
                    return true;
                }
            case FOX_PLUS_X:
            case FOX_MINUS_X:
                if (move.getStartPoint().x > move.getEndPoint().x + 1) {
                    for (int x = move.getEndPoint().x; x < move.getStartPoint().x - 1; x++) {
                        point = new Point(x, move.getStartPoint().y);
                        if (board.hasSquare(point) && board.getSquare(point).hasPiece()) {
                            return false;
                        }
                    }
                    return true;
                } else if (move.getStartPoint().x < move.getEndPoint().x - 1) {
                    for (int x = move.getStartPoint().x + 1; x < move.getEndPoint().x; x++) {
                        point = new Point(x, move.getStartPoint().y);
                        if (board.hasSquare(point) && board.getSquare(point).hasPiece()) {
                            return false;
                        }
                    }
                    return true;
                }
        }
        return false;
    }
}
