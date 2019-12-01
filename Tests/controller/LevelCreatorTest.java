package controller;

import helpers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class LevelCreatorTest {

    @BeforeEach
    void setUp() {
        LevelCreator.resetLevel();
    }

    @Test
    void testClearSquare() {
        //Test clearing Rabbit
        Point rabbitLoc = new Point(1,1);
        LevelCreator.placePiece(new Rabbit(rabbitLoc));
        assertTrue(LevelCreator.board.hasPiece(rabbitLoc));

        LevelCreator.clearSquare(rabbitLoc);
        assertFalse(LevelCreator.board.hasPiece(rabbitLoc));

        //Test clearing Hole
        Point holeLoc = new Point(2,2);
        LevelCreator.placeHole(holeLoc);
        assertTrue(LevelCreator.board.getTerrain().get(holeLoc).isHole());

        LevelCreator.clearSquare(holeLoc);
        assertFalse(LevelCreator.board.getTerrain().containsKey(holeLoc));
    }

    @Test
    void testGetAvailSpotsRabbit() {
        Rabbit rabbit = new Rabbit(new Point());

        //Test empty board
        Set<Point> comp1 = LevelCreator.getAvailableSpots(rabbit);
        Set<Point> expected1 = new HashSet<>();
        //Add all the points on the board
        for(int x = 0; x < 5; x++) {
            for(int y = 0; y < 5; y++) {
                expected1.add(new Point(x, y));
            }
        }
        assertEquals(expected1, comp1);

        //Test with a mushroom on the board
        Point mushroomLoc = new Point(1,1);
        LevelCreator.placePiece(new Mushroom(mushroomLoc));
        expected1.remove(mushroomLoc); //The point the mushroom occupies should no longer be valid
        Set<Point> comp2 = LevelCreator.getAvailableSpots(rabbit);
        assertEquals(expected1, comp2);

        //Test with a Fox on the board
        Fox fox = new Fox(Direction.Y_AXIS, new Point(3,1));
    }

    @Test
    void testGetAvailSpotsFoxes() {
        Fox xFox = new Fox(Direction.X_AXIS, new Point(1,0));
        Fox yFox = new Fox(Direction.Y_AXIS, new Point(0,1));

        //Test empty board
        Set<Point> comp1 = LevelCreator.getAvailableSpots(xFox);
        Set<Point> expected1 = new HashSet<>(Set.of(new Point(1,1), new Point(2,1), new Point(3,1), new Point(4,1),
                new Point(1,3), new Point(2,3), new Point(3,3), new Point(4,3)));
        assertEquals(expected1, comp1);

        Set<Point> comp2 = LevelCreator.getAvailableSpots(yFox);
        Set<Point> expected2 = new HashSet<>(Set.of(new Point(1,1), new Point(1,2), new Point(1,3), new Point(1,4),
                new Point(3,1), new Point(3,2), new Point(3,3), new Point(3,4)));
        assertEquals(expected2, comp2);

        //Test going over other pieces
        Point mushroomLoc = new Point(1,1);
        LevelCreator.placePiece(new Mushroom(mushroomLoc));
        //X-Foxes
        expected1.remove(mushroomLoc);
        expected1.remove(new Point(mushroomLoc.x+1, mushroomLoc.y));
        Set<Point> comp3 = LevelCreator.getAvailableSpots(xFox);
        assertEquals(expected1, comp3);
        //Y-Foxes
        expected2.remove(mushroomLoc);
        expected2.remove(new Point(mushroomLoc.x, mushroomLoc.y+1));
        Set<Point> comp4 = LevelCreator.getAvailableSpots(yFox);
        assertEquals(expected2, comp4);

        //Test going over Holes
        Point holeLoc = new Point(3,3);
        LevelCreator.placeHole(holeLoc);
        //X-Foxes
        Set<Point> comp5 = LevelCreator.getAvailableSpots(xFox);
        expected1.remove(holeLoc);
        expected1.remove(new Point(holeLoc.x+1, holeLoc.y));
        assertEquals(expected1, comp5);
        //Y-Foxes
        Set<Point> comp6 = LevelCreator.getAvailableSpots(yFox);
        expected2.remove(holeLoc);
        expected2.remove(new Point(holeLoc.x, holeLoc.y+1));
        assertEquals(expected2, comp6);
    }
}
