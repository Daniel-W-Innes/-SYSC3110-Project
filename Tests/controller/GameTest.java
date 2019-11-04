package controller;

import helpers.Move;
import model.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.Gui;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Game game = null;

    // Note:  Window will briefly appear as a new GUI is temporarily created in each of the tests

    @BeforeEach
    void testSetUp() {
        game = new Game();
    }

    @Test
    void setUp() {
        game.setUp(new Gui(game), 1);
        testFirstLevelCreation();
    }

    @Test
    void movePiece() {
        game.setUp(new Gui(game), 1);
        List<Move> possibleMoves =  game.getMoves(new Point(2, 3));
        game.movePiece(new Move(new Point(2, 3), new Point(0, 3)));

        assertTrue(game.getMoves(new Point(2, 3)).isEmpty());

        possibleMoves = game.getMoves(new Point(0, 3));
        assertEquals(2, possibleMoves.size());

        Set<Move> expectedMoves = new HashSet<>();
        expectedMoves.add(new Move(new Point(0, 3), new Point(0, 0)));
        expectedMoves.add(new Move(new Point(0, 3), new Point(2, 3)));

        for(Move move : possibleMoves) {
            assertTrue(expectedMoves.contains(move));
        }
    }

    @Test
    void setLevel() {
        game.setUp(new Gui(game), 1);
        game.resetLevel(new Gui(game));
        testFirstLevelCreation();
    }

    @Test
    void resetLevel() {
        game.setUp(new Gui(game), 1);
        game.resetLevel(new Gui(game));
        testFirstLevelCreation();
    }


    void testFirstLevelCreation() {
        // Test possible moves for the only rabbit
        List<Move> possibleMoves =  game.getMoves(new Point(2, 3));
        assertEquals(1, possibleMoves.size());
        assertEquals(new Point(0, 3), possibleMoves.get(0).getEndPoint());

        // Test possible moves for mushroom
        assertTrue(game.getMoves(new Point(0, 1)).isEmpty());
    }
}