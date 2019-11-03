package view;

import helpers.Piece;
import model.Board;

import java.awt.*;

public interface View {
    void addPiece(Point point, Piece piece);
    void removePiece(Point point);
    void notifyWin();
    void sendInitialBoard(Board b);
}
