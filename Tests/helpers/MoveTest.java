package helpers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class MoveTest {

    private Move move;
    private Move similarMove;
    private Move differentMove;

    @BeforeEach
    void setUp() {
        move = new Move(new Point(1, 1), new Point(2, 2));
        similarMove = new Move(new Point(1, 1), new Point(2, 2));
        differentMove = new Move(new Point(3, 4), new Point(2, 2));
    }

    @Test
    void testGetReverse() {
        assertEquals(new Move(new Point(2, 2), new Point(1, 1)), move.getReverse());
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