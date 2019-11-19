package view;

import helpers.Piece;
import helpers.Point;
import model.Board;


/**
 * The interface that a view must implement.
 */

public interface View {
    void addPiece(Point point, Piece piece);

    void removePiece(Point point);

    void notifyWin();

    void sendInitialBoard(Board board);
}
