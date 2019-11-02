package view;

import helpers.Piece;

import java.awt.*;

public interface View {
    //I guess these are the "update()" methods?
    void addPiece(Point point, Piece piece);

    void removePiece(Point point);
}
