package backend.helpers;

import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;
import utilities.Points;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

class PieceTest {
    private static final int MIN = 0;
    private static final int MAX = 5;

    private static final Map<Set<Point>, PieceImp> PIECE_IMPS = Sets.combinations(Points.combinations(MIN, MAX), 2)
            .stream()
            .collect(Collectors.toMap(points -> points, PieceImp::new));
    ;

    private static class PieceImp extends Piece {
        private final Set<Point> points;

        PieceImp(Set<Point> points) {
            this.points = points;
        }

        @Override
        public Set<Move> getMoves(Board board, Point point) {
            return new HashSet<>();
        }

        @Override
        public boolean occupies(Point point) {
            return points.contains(point);
        }

        @Override
        public Set<Point> occupies() {
            return points;
        }
    }

    @Test
    void getFunnel() {

    }
}