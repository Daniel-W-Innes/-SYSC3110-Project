package controller;

import model.Board;
import model.Move;
import model.Square;

import java.awt.*;

class Foxes {
    private static void move(Board board, Move move) {
        if (board.hasSquare(move.getEndPoint())) {
            board.getSquare(move.getEndPoint()).setPiece(board.getSquare(move.getStartPoint()).getPiece());
        } else {
            board.addSquare(move.getEndPoint(), new Square(false, false, board.getSquare(move.getStartPoint()).getPiece()));
        }
        board.getSquare(move.getStartPoint()).removePiece();
        board.removeSquareIfEmpty(move.getStartPoint());
    }

    static boolean checkAndMove(Board board, Move move) {
        Point point;
        switch (board.getSquare(move.getStartPoint()).getPiece()) {
            case FOX_PLUS_Y:
                if (move.isPlusY()) {
                    //Debug: System.out.println("FOX_PLUS_Y move.isPlusY");
                    for (int y = move.getStartPoint().y + 1; y <= move.getEndPoint().y; y++) {
                        point = new Point(move.getStartPoint().x, y);
                        if (board.hasSquare(point) && board.getSquare(point).hasPiece() || point.y > board.getMax().y) {
                            return false;
                        }
                    }
                    move(board, move);
                    move(board, new Move(new Point(move.getStartPoint().x, move.getStartPoint().y - 1), new Point(move.getEndPoint().x, move.getEndPoint().y - 1)));
                    board.notifyObserver();
                    return true;
                } else if (move.isMinusY()) {
                    //Debug: System.out.println("FOX_PLUS_Y move.isMinusY");
                    for (int y = move.getEndPoint().y - 1; y <= move.getStartPoint().y - 2; y++) {
                        point = new Point(move.getStartPoint().x, y);
                        if (board.hasSquare(point) && board.getSquare(point).hasPiece() || point.y < 0) {
                            return false;
                        }
                    }
                    move(board, move);
                    move(board, new Move(new Point(move.getStartPoint().x, move.getStartPoint().y - 1), new Point(move.getEndPoint().x, move.getEndPoint().y - 1)));
                    board.notifyObserver();
                    return true;
                }
            case FOX_MINUS_Y:
                if (move.isPlusY()) {
                    //Debug: System.out.println("FOX_MINUS_Y move.isPlusY");
                    for (int y = move.getStartPoint().y + 2; y <= move.getEndPoint().y + 1; y++) {
                        point = new Point(move.getStartPoint().x, y);
                        if (board.hasSquare(point) && board.getSquare(point).hasPiece() || point.y > board.getMax().y) {
                            return false;
                        }
                    }
                    move(board, move);
                    move(board, new Move(new Point(move.getStartPoint().x, move.getStartPoint().y + 1), new Point(move.getEndPoint().x, move.getEndPoint().y + 1)));
                    board.notifyObserver();
                    return true;
                } else if (move.isMinusY()) {
                    //Debug: System.out.println("FOX_MINUS_Y move.isMinusY");
                    for (int y = move.getEndPoint().y; y < move.getStartPoint().y - 1; y++) {
                        point = new Point(move.getStartPoint().x, y);
                        if (board.hasSquare(point) && board.getSquare(point).hasPiece() || point.y < 0) {
                            return false;
                        }
                    }
                    move(board, move);
                    move(board, new Move(new Point(move.getStartPoint().x, move.getStartPoint().y + 1), new Point(move.getEndPoint().x, move.getEndPoint().y + 1)));
                    board.notifyObserver();
                    return true;
                }
            case FOX_PLUS_X:
                if (move.isPlusX()) {
                    //Debug: System.out.println("FOX_PLUS_X move.isPlusX");
                    for (int x = move.getStartPoint().x + 1; x <= move.getEndPoint().x; x++) {
                        point = new Point(x, move.getStartPoint().y);
                        if (board.hasSquare(point) && board.getSquare(point).hasPiece() || point.x > board.getMax().x) {
                            return false;
                        }
                    }
                    move(board, move);
                    move(board, new Move(new Point(move.getStartPoint().x - 1, move.getStartPoint().y), new Point(move.getEndPoint().x - 1, move.getEndPoint().y)));
                    board.notifyObserver();
                    return true;
                } else if (move.isMinusX()) {
                    //Debug: System.out.println("FOX_PLUS_X move.isMinusX");
                    for (int x = move.getEndPoint().x - 1; x <= move.getStartPoint().x - 2; x++) {
                        point = new Point(x, move.getStartPoint().y);
                        if (board.hasSquare(point) && board.getSquare(point).hasPiece() || point.x < 0) {
                            return false;
                        }
                    }
                    move(board, move);
                    move(board, new Move(new Point(move.getStartPoint().x - 1, move.getStartPoint().y), new Point(move.getEndPoint().x - 1, move.getEndPoint().y)));
                    board.notifyObserver();
                    return true;
                }
            case FOX_MINUS_X:
                if (move.isPlusX()) {
                    //Debug: System.out.println("FOX_MINUS_X move.isPlusX");
                    for (int x = move.getStartPoint().x + 2; x <= move.getEndPoint().x + 1; x++) {
                        point = new Point(x, move.getStartPoint().y);
                        if (board.hasSquare(point) && board.getSquare(point).hasPiece() || point.x > board.getMax().x) {
                            return false;
                        }
                    }
                    move(board, move);
                    move(board, new Move(new Point(move.getStartPoint().x + 1, move.getStartPoint().y), new Point(move.getEndPoint().x + 1, move.getEndPoint().y)));
                    board.notifyObserver();
                    return true;
                } else if (move.isMinusX()) {
                    //Debug: System.out.println("FOX_MINUS_X move.isMinusX");
                    for (int x = move.getEndPoint().x; x <= move.getStartPoint().x - 1; x++) {
                        point = new Point(x, move.getStartPoint().y);
                        if (board.hasSquare(point) && board.getSquare(point).hasPiece() || point.x < 0) {
                            return false;
                        }
                    }
                    move(board, move);
                    move(board, new Move(new Point(move.getStartPoint().x + 1, move.getStartPoint().y), new Point(move.getEndPoint().x + 1, move.getEndPoint().y)));
                    board.notifyObserver();
                    return true;
                }
        }
        return false;
    }
}
