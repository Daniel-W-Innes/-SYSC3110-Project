package backend.helpers;

import org.junit.jupiter.api.Test;
import utilities.Points;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class MushroomTest {
    private static final int MIN = 0;
    private static final int MAX = 11;

    private static final Map<Point, Mushroom> MUSHROOMS = Points.combinations(MIN, MAX)
            .stream()
            .collect(Collectors.toMap(point -> point, Mushroom::new));

    @Test
    void testToString() {
        for (Mushroom mushroom : MUSHROOMS.values()) {
            assertEquals("M", mushroom.toString());
        }
    }

    @Test
    void testEquals() {
        for (Map.Entry<Point, Mushroom> entry1 : MUSHROOMS.entrySet()) {
            for (Map.Entry<Point, Mushroom> entry2 : MUSHROOMS.entrySet()) {
                assertEquals(entry1.getKey().equals(entry2.getKey()), entry1.getValue().equals(entry2.getValue()));
            }
        }
    }

    @Test
    void testHashCode() {
        for (Map.Entry<Point, Mushroom> entry1 : MUSHROOMS.entrySet()) {
            for (Map.Entry<Point, Mushroom> entry2 : MUSHROOMS.entrySet()) {
                assertEquals(entry1.getKey().hashCode() == entry2.getKey().hashCode(), entry1.getValue().hashCode() == entry2.getValue().hashCode());
            }
        }
    }

    @Test
    void getMoves() {
        //TODO
    }

    @Test
    void testOccupiesVoid() {
        for (Map.Entry<Point, Mushroom> entry : MUSHROOMS.entrySet()) {
            assertEquals(Set.of(entry.getKey()), entry.getValue().occupies());
        }
    }

    @Test
    void testOccupiesPoint() {
        for (Map.Entry<Point, Mushroom> entry : MUSHROOMS.entrySet()) {
            assertTrue(entry.getValue().occupies(entry.getKey()));
            assertFalse(entry.getValue().occupies(new Point(MIN - 1, MIN - 1)));
        }
    }
}