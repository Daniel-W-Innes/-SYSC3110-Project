package backend.helpers;

import backend.models.MutableBoard;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Rabbit extends Piece {

    private final Color color;
    private final Point point;

    Rabbit(MutableBoard mutableBoard, Color color, Point point) {
        super(mutableBoard);
        this.color = color;
        this.point = point;
    }

    private void addMove(Set<Move> moves, Point offset) {
        boolean c = true;
        Point point = new Point(this.point);
        while (c) {
            point = new Point(point.x + offset.x, point.y + offset.y);
            c = getMutableBoard().hasPiece(point);
        }
        if (!this.point.equals(new Point(point.x - offset.x, point.y - offset.y)) && point.y <= getMutableBoard().getMax().y && point.x <= getMutableBoard().getMax().x && point.y >= 0 && point.x >= 0) {
            moves.add(new Move(this, new Rabbit(getMutableBoard(), color, point)));
        }
    }

    @Override
    public Set<Move> getMoves(Point point) {
        Set<Move> moves = new HashSet<>();
        addMove(moves, new Point(0, 1));
        addMove(moves, new Point(0, -1));
        addMove(moves, new Point(1, 0));
        addMove(moves, new Point(-1, 0));
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
        if (obj instanceof Rabbit) {
            return super.equals(obj) && color.equals(((Rabbit) obj).color) && point.equals(((Rabbit) obj).point);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new int[]{super.hashCode(), color.hashCode(), point.hashCode()});
    }

    public Color getColor() {
        return color;
    }
}
