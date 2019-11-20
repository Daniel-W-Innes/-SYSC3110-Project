package controller;

import helpers.Move;
import helpers.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.Gui;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
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
        List<Move> possibleMoves;
        game.movePiece(new Move(new Point(2, 3), new Point(0, 3)), false);

        assertTrue(game.getMoves(new Point(2, 3)).isEmpty());

        possibleMoves = game.getMoves(new Point(0, 3));
        assertEquals(2, possibleMoves.size());

        Set<Move> expectedMoves = new HashSet<>();
        expectedMoves.add(new Move(new Point(0, 3), new Point(0, 0)));
        expectedMoves.add(new Move(new Point(0, 3), new Point(2, 3)));

        for (Move move : possibleMoves) {
            assertTrue(expectedMoves.contains(move));
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
    void testUndo() throws InterruptedException {

        // Make sure a solution is generated before making a move, as the movePiece function requests from the solution
        Thread.sleep(200);

        game.movePiece(new Move(new Point(2, 3), new Point(0, 3)), false);

        game.undo();

        game.movePiece(new Move(new Point(2, 3), new Point(0, 3)), false);
    }

    @Test
    void testRedo() throws InterruptedException {
        // Make sure a solution is generated before making a move, as the movePiece function requests from the solution
        Thread.sleep(1000);
        game.movePiece(new Move(new Point(2, 3), new Point(0, 3)), false);

        game.undo();

        // Make sure a solution is generated before making a redo, as the undo forces a new solution to be created
        // as it moves a piece not part of the solution
        Thread.sleep(1000);

        game.redo();

        game.movePiece(new Move(new Point(0, 3), new Point(0, 0)), false);
    }

    void testFirstLevelCreation() {
        // Test possible moves for the only rabbit
        List<Move> possibleMoves = game.getMoves(new Point(2, 3));
        assertEquals(1, possibleMoves.size());
        assertEquals(new Point(0, 3), possibleMoves.get(0).getEndPoint());

        // Test possible moves for mushroom
        assertTrue(game.getMoves(new Point(0, 1)).isEmpty());
    }
}