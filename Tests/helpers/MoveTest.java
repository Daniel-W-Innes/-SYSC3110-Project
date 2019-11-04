package helpers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest {

    Move move = null;
    Move similarMove = null;
    Move differentMove = null;

    @BeforeEach
    private void setUp() {
        move = new Move(new Point(1, 1), new Point(2, 2));
        similarMove = new Move(new Point(1, 1), new Point(2, 2));
        differentMove = new Move(new Point(3, 4), new Point(2, 2));
    }

    @Test
    void getEnd() {
        assertEquals(new Point(2, 2), move.getEndPoint());
        assertEquals(new Point(2, 2), differentMove.getEndPoint());
    }

    @Test
    void getStart() {
        assertEquals(new Point(1, 1), move.getStartPoint());
        assertEquals(new Point(3, 4), differentMove.getStartPoint());
    }

    @Test
    void testEquals() {
        assertEquals(similarMove, move);
        assertNotEquals(differentMove, move);
    }

    @Test
    void testHashCode() {
        assertEquals(similarMove.hashCode(), move.hashCode());
        assertNotEquals(differentMove, move);
    }

    @Test
    void testToString() {
        assertEquals("{1, 1} -> {2, 2}", move.toString());
        assertEquals("{3, 4} -> {2, 2}", differentMove.toString());
    }
}