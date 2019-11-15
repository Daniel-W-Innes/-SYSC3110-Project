package backend.helpers;

import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;
import utilities.Points;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FoxTest {
    private static final int MIN = 0;
    private static final int MAX = 5;

    private static final Map<Set<Point>, Fox> FOXES = Sets.combinations(Points.combinations(MIN, MAX), 2)
            .stream()
            .collect(Collectors.toMap(points ->points, points -> new Fox(points.toArray(new Point[2])[0],points.toArray(new Point[2])[1])));

    @Test
    void testEquals() {
        for (Map.Entry<Set<Point>, Fox> entry1 : FOXES.entrySet()) {
            for (Map.Entry<Set<Point>, Fox> entry2 : FOXES.entrySet()) {
                assertEquals(entry1.getKey().equals(entry2.getKey()), entry1.getValue().equals(entry2.getValue()));
            }
        }
    }

    @Test
    void testHashCode() {
        for (Map.Entry<Set<Point>, Fox> entry1 : FOXES.entrySet()) {
            for (Map.Entry<Set<Point>, Fox> entry2 : FOXES.entrySet()) {
                assertEquals(entry1.getKey().hashCode() == entry2.getKey().hashCode(), entry1.getValue().hashCode() == entry2.getValue().hashCode());
            }
        }
    }
}