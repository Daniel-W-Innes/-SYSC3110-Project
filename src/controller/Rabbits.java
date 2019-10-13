package controller;

import model.Board;
import model.MoveCommand;

import java.awt.*;


public class Rabbits {
    public static boolean checkMove(Board board, MoveCommand moveCommand) {
        if (board.hasSquare(moveCommand.getTo()) && board.getSquare(moveCommand.getTo()).hasPiece()) {
            Point point;
            if (moveCommand.getFrom().x == moveCommand.getTo().x) {
                if (moveCommand.getFrom().y > moveCommand.getTo().y + 1) {
                    for (int y = moveCommand.getTo().y + 1; y < moveCommand.getFrom().y - 1; y++) {
                        point = new Point(moveCommand.getFrom().x, y);
                        if (!board.hasSquare(point) || !board.getSquare(point).hasPiece()) {
                            return false;
                        }
                    }
                    return true;
                } else if (moveCommand.getFrom().y < moveCommand.getTo().y - 1) {
                    for (int y = moveCommand.getFrom().y + 1; y < moveCommand.getTo().y - 1; y++) {
                        point = new Point(moveCommand.getFrom().x, y);
                        if (!board.hasSquare(point) || !board.getSquare(point).hasPiece()) {
                            return false;
                        }
                    }
                    return true;
                }
            } else if (moveCommand.getFrom().y == moveCommand.getTo().y) {
                if (moveCommand.getFrom().x > moveCommand.getTo().x + 1) {
                    for (int x = moveCommand.getTo().x + 1; x < moveCommand.getFrom().x - 1; x++) {
                        point = new Point(x, moveCommand.getFrom().y);
                        if (!board.hasSquare(point) || !board.getSquare(point).hasPiece()) {
                            return false;
                        }
                    }
                    return true;
                } else if (moveCommand.getFrom().x < moveCommand.getTo().x - 1) {
                    for (int x = moveCommand.getFrom().x + 1; x < moveCommand.getTo().x - 1; x++) {
                        point = new Point(x, moveCommand.getFrom().y);
                        if (!board.hasSquare(point) || !board.getSquare(point).hasPiece()) {
                            return false;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
