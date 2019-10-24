package helpers;

import model.Board;

import java.awt.*;
import java.util.Map;

public interface Piece {
    Map<Move, Board> getMoves(Board board, Point point);
}
