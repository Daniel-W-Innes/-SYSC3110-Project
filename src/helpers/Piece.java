package helpers;

import model.Board;

import java.awt.*;
import java.util.Map;
import java.util.Set;

public interface Piece {
    Map<Move, Set<Move>> getMoves(Board board, Point point);
}
