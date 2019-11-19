package helpers;

import model.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MushroomTest {
    private Mushroom mushroom;

    @BeforeEach
    void setUp() {
        mushroom = new Mushroom(new Point(0, 0));
    }

    @Test
    void testClone() {
        Piece newMushroom = mushroom.clonePiece();

        assertTrue(newMushroom instanceof  Mushroom);
        assertEquals(newMushroom, mushroom);
    }

    @Test
    void testHash() {
        Mushroom similarMushroom = (Mushroom)mushroom.clonePiece();
        Mushroom differentMushroom = new Mushroom( new Point(0, 1));

        assertEquals(similarMushroom.hashCode(), mushroom.hashCode());
        assertNotEquals(differentMushroom.hashCode(), mushroom.hashCode());
    }

    @Test
    void testBoardSpotsUsed() {
        assertEquals(1, mushroom.boardSpotsUsed().size());
        assertTrue(mushroom.boardSpotsUsed().contains(new Point(0, 0)));
    }

    @Test
    void testUpdateBoardSpotUsed() {
        mushroom.updateBoardSpotUsed(new Point(1, 1));
        assertEquals(1, mushroom.boardSpotsUsed().size());
        assertTrue(mushroom.boardSpotsUsed().contains(new Point(1, 1)));
    }

    @Test
    void testGetMoves() {
        assertTrue(mushroom.getMoves(new Board(), new Point(1, 1)).isEmpty());
    }

    @Test
    void testGetImageIcon() {
        assertEquals(Mushroom.imageIconLocation, mushroom.getImageIcon(new Point()).getDescription());
    }
}
