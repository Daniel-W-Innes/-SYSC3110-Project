package view;

import helpers.Piece;

import java.awt.*;

public interface View {
    void addPiece(Point point, Piece piece);

    void removePiece(Point point);
}
