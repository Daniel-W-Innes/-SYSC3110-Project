package helpers;

import model.Board;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FoxesTest {

    @Test
    void testGetMovesX() {
        Board board = new Board();

        board.addSquare(new Point(0, 0), new Square(false, false));
        board.addSquare(new Point(4, 0), new Square(false, false));

        Foxes fox = new Foxes(Foxes.Direction.X_AXIS, new Point(2, 0));

        board.addPiece(new Point(2, 0), fox);

        assertEquals(1, fox.getMoves(board).size());
        assertEquals(new Move(new Point(2, 0), new Point(3, 0)), fox.getMoves(board).get(0));
    }

    @Test
    void testGetMovesY() {
        Board board = new Board();

        Foxes fox = new Foxes(Foxes.Direction.Y_AXIS, new Point(0, 2));

        board.addPiece(new Point(0, 2), fox);

        Set<Move> possibleMoves = new HashSet<>();
        possibleMoves.add(new Move(new Point(0, 2), new Point(0, 3)));
        possibleMoves.add(new Move(new Point(0, 2), new Point(0, 4)));
        possibleMoves.add(new Move(new Point(0, 2), new Point(0, 1)));

        assertEquals(possibleMoves.size(), fox.getMoves(board).size());

        for(int i = 0; i < possibleMoves.size(); i++) {
            assertTrue(possibleMoves.contains(fox.getMoves(board).get(i)));
        }
    }
}