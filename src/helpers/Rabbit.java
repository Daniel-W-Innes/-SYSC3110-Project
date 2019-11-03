package helpers;

import model.Board;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rabbit implements Piece {

    private final Color color;

    public Rabbit(Color color) {
        this.color = color;
    }

    private static final ImageIcon icon = new ImageIcon("./resources/Rabbit_brown.jpg");

    @Override
    public ImageIcon getIcon() {
        return icon;
    }

    /**
     *
     * @param board
     * @param start
     * @param offset
     * @return
     */
    private Map<Move, List<Move>> getMoves(Board board, Point start, Point offset) {
        boolean c = true;
        Map<Move, List<Move>> moves = new HashMap<>();
        Move move;
        Point point = new Point(start);

        while (c) {
            point = new Point(point.x + offset.x, point.y + offset.y);
            c = board.hasPiece(point);
        }
        if (!start.equals(new Point(point.x - offset.x, point.y - offset.y)) && point.y <= board.getMax().y && point.x <= board.getMax().x && point.y >= 0 && point.x >= 0) {
            move = new Move(start, point);
            moves.put(move, List.of(move)); //Hello???
        }
        return moves;
    }

    @Override
    public Map<Move, List<Move>> getMoves(Board board, Point start) {
        Map<Move, List<Move>> moves = new HashMap<>();
        //Get the possible moves in each direction
        moves.putAll(getMoves(board, start, new Point(0, 1)));
        moves.putAll(getMoves(board, start, new Point(0, -1)));
        moves.putAll(getMoves(board, start, new Point(1, 0)));
        moves.putAll(getMoves(board, start, new Point(-1, 0)));
        return moves;
    }

    @Override
    public String toString() {
        return "R";
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj != null && obj.getClass() == this.getClass();
    }

    @Override
    public int hashCode() {
        return 1;
    }

    public Color getColor() {
        return color;
    }
}
