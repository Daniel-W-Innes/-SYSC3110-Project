package helpers;

import model.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MushroomTest {
    Mushroom mushroom = null;

    @BeforeEach
    void setUp() {
        mushroom = new Mushroom(new Point(0, 0));
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
