package helpers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SquareTest {

    private Square square;
    private Square similarSquare;
    private Square differentSquare;

    @BeforeEach
    void setUp() {
        square = new Square(true);
        similarSquare = new Square(true);
        differentSquare = new Square(false);
    }

    @Test
    void isHole() {
        assertTrue(square.isHole());
    }

    @Test
    void testEquals() {
        assertEquals(similarSquare, square);
        assertNotEquals(differentSquare, square);
    }

    @Test
    void testHashCode() {
        assertEquals(square.hashCode(), similarSquare.hashCode());
        assertNotEquals(square.hashCode(), differentSquare.hashCode());
    }
}