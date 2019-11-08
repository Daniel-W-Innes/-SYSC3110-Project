package frontend;

import backend.helpers.Piece;
import backend.helpers.Point;

public interface View {
    void addPiece(Point point, Piece piece);

    void removePiece(Point point);
}
