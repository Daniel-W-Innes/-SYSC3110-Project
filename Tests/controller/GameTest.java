package controller;

import helpers.Move;
import helpers.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.Gui;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;

    // Note:  Window will briefly appear as a new GUI is temporarily created in each of the tests

    @BeforeEach
    void setUp() {
        try {
            game = new Game("1");
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void testSetUp() {
        testFirstLevelCreation();
    }

    @Test
    void movePiece() {
        Set<Point> possibleEndPoint;
        game.movePiece(new Move(new Point(2, 3), new Point(0, 3)), false);

        assertTrue(game.getEndPoint(new Point(2, 3)).isEmpty());

        possibleEndPoint = game.getEndPoint(new Point(0, 3));
        assertEquals(2, possibleEndPoint.size());

        Set<Point> expectedEndPoint = new HashSet<>();
        expectedEndPoint.add(new Point(0, 0));
        expectedEndPoint.add(new Point(2, 3));

        for (Point point : possibleEndPoint) {
            assertTrue(expectedEndPoint.contains(point));
        }
    }

    @Test
    void setLevel() {
        try {
            game.setLevel(new Gui(game), "1");
        } catch (IOException e) {
            fail();
        }
        testFirstLevelCreation();
    }

    @Test
    void resetLevel() {
        try {
            game.resetLevel(new Gui(game));
        } catch (IOException e) {
            fail();
        }
        testFirstLevelCreation();
    }

    /*
        For the undo / redo tests, since the board is private and so whether or not a piece is at a location cannot
        be tested against directly, the moves are done knowing the state of the board and where they should be.

        If either undo or redo fails, then there won't be a piece at the hard-coded locations, and an exception will be
        thrown, indicating a failure.
     */

    @Test
    void testUndo() {
        game.movePiece(new Move(new Point(2, 3), new Point(0, 3)));
        game.undo();
        game.movePiece(new Move(new Point(2, 3), new Point(0, 3)));
    }

    @Test
    void testRedo() {
        game.movePiece(new Move(new Point(2, 3), new Point(0, 3)));
        game.undo();
        game.redo();
        game.movePiece(new Move(new Point(0, 3), new Point(0, 0)));
    }

    void testFirstLevelCreation() {
        // Test possible moves for the only rabbit
        Set<Point> possibleEndPoint = game.getEndPoint(new Point(2, 3));
        assertEquals(1, possibleEndPoint.size());
        assertTrue(possibleEndPoint.contains(new Point(0, 3)));

        // Test possible moves for mushroom
        assertTrue(game.getEndPoint(new Point(0, 1)).isEmpty());
    }
}