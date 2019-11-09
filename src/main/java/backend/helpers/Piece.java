package backend.helpers;

import com.google.common.hash.Funnel;

import java.util.Set;

public abstract class Piece {

    public abstract Set<Move> getMoves(Board board, Point point);

    public abstract boolean occupies(Point point);

    public abstract Set<Point> occupies();

    public Funnel<Piece> getFunnel() {
        return (Funnel<Piece>) (from, into) -> {
            for (Point point : from.occupies()) {
                point.getFunnel().funnel(point, into);
            }
        };
    }
}
