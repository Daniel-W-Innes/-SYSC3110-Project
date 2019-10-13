package controller;

import model.Board;
import model.MoveCommand;

import java.awt.*;

class Foxes {
    static boolean checkMove(Board board, MoveCommand moveCommand) {
        Point point;
        switch (board.getSquare(moveCommand.getFrom()).getPiece()) {
            case FOX_PLUS_Y:
            case FOX_MINUS_Y:
                if (moveCommand.getFrom().y > moveCommand.getFrom().y) {
                    for (int y = moveCommand.getTo().y; y < moveCommand.getFrom().y - 1; y++) {
                        point = new Point(moveCommand.getFrom().x, y);
                        if (board.hasSquare(point) && board.getSquare(point).hasPiece()) {
                            return false;
                        }
                    }
                    return true;
                } else if (moveCommand.getFrom().y < moveCommand.getTo().y - 1) {
                    for (int y = moveCommand.getFrom().y + 1; y < moveCommand.getTo().y; y++) {
                        point = new Point(moveCommand.getFrom().x, y);
                        if (board.hasSquare(point) && board.getSquare(point).hasPiece()) {
                            return false;
                        }
                    }
                    return true;
                }
            case FOX_PLUS_X:
            case FOX_MINUS_X:
                if (moveCommand.getFrom().x > moveCommand.getTo().x + 1) {
                    for (int x = moveCommand.getTo().x; x < moveCommand.getFrom().x - 1; x++) {
                        point = new Point(x, moveCommand.getFrom().y);
                        if (board.hasSquare(point) && board.getSquare(point).hasPiece()) {
                            return false;
                        }
                    }
                    return true;
                } else if (moveCommand.getFrom().x < moveCommand.getTo().x - 1) {
                    for (int x = moveCommand.getFrom().x + 1; x < moveCommand.getTo().x; x++) {
                        point = new Point(x, moveCommand.getFrom().y);
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
