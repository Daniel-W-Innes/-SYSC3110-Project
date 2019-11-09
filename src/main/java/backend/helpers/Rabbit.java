package backend.helpers;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Rabbit implements Piece {

    private final Color color;
    private final Point point;

    Rabbit( Color color, Point point) {
        this.color = color;
        this.point = point;
    }

    private void addMove(Set<Move> moves, ImmutableBoard board, Point offset) {
        boolean c = true;
        Point point = new Point(this.point);
        while (c) {
            point = new Point(point.x + offset.x, point.y + offset.y);
            c = board.hasPiece(point);
        }
        if (!this.point.equals(new Point(point.x - offset.x, point.y - offset.y))
                && point.y <= board.getMax().y
                && point.x <= board.getMax().x
                && point.y >= 0
                && point.x >= 0) {
            moves.add(new Move(this, new Rabbit(color, point)));
        }
    }

    @Override
    public Set<Move> getMoves(ImmutableBoard board, Point point) {
        Set<Move> moves = new HashSet<>();
        addMove(moves, board, new Point(0, 1));
        addMove(moves, board, new Point(0, -1));
        addMove(moves, board, new Point(1, 0));
        addMove(moves, board, new Point(-1, 0));
        return moves;
    }

    @Override
    public boolean occupies(Point point) {
        return this.point.equals(point);
    }

    @Override
    public Set<Point> occupies() {
        return Set.of(point);
    }

    @Override
    public String toString() {
        return "R";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Rabbit)) {
            return false;
        }
        Rabbit rabbit = (Rabbit) obj;
        return color.equals(rabbit.color) && point.equals(rabbit.point);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new int[]{color.hashCode(), point.hashCode()});
    }

    public Color getColor() {
        return color;
    }
}
