package view;

import helpers.Move;
import helpers.Piece;
import model.Board;

import java.awt.*;

/**
 * The interface that a view must implement.
 */

public interface View {
    void addPiece(Point point, Piece piece);

    void showHint(Move move);

    void removePiece(Point point);

    void notifyWin();

    void sendInitialBoard(Board board);
}
