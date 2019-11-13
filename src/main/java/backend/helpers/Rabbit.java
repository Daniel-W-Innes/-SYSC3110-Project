package backend.helpers;

import com.google.common.hash.Funnel;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public final class Rabbit extends Piece {

    private final Color color;
    private final Point point;
    private final HashCode hashCode;

    Rabbit( Color color, Point point) {
        this.color = color;
        this.point = point;
        hashCode = Hashing.murmur3_128().newHasher()
                .putInt(color.getRGB())
                .putObject(point, point.getFunnel())
                .hash();
    }

    private void addMove(Set<Move> moves, Board board, Point offset) {
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
    public Set<Move> getMoves(Board board, Point point) {
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
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Rabbit rabbit = (Rabbit) obj;
        return color.equals(rabbit.color) && point.equals(rabbit.point);
    }

    @Override
    public Funnel<Piece> getFunnel() {
        return (from, into) -> {
            Rabbit rabbit = (Rabbit) from;
            into.putInt(rabbit.color.getRGB());
            rabbit.point.getFunnel().funnel(rabbit.point, into);

        };
    }

    @Override
    public int hashCode() {
        return hashCode.asInt();
    }

    public Color getColor() {
        return color;
    }
}
