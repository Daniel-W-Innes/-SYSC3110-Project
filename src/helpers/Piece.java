package helpers;

import model.Board;

import java.awt.*;
import java.util.List;
import java.util.Map;

public interface Piece {
    Map<Move, List<Move>> getMoves(Board board, Point point);
}
