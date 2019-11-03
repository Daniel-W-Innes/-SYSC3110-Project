package helpers;

import model.Board;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public interface Piece {
    /**
     * Given a Board and Point, return a Map of possible moves.
     * @param board
     * @param point
     * @return
     */
    Map<Move, List<Move>> getMoves(Board board, Point point);

    ImageIcon getIcon();
}
