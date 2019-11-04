package model;


import helpers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @Test
    void addSquare() {
        Map<Point, Square> squareLocations = Map.of(
                new Point(0, 0), new Square(true, false),
                new Point(1, 1), new Square(true, true),
                new Point(4, 4), new Square(false, false));
        for (Map.Entry<Point, Square> squareEntry : squareLocations.entrySet()) {
            board.addSquare(squareEntry.getKey(), squareEntry.getValue());
        }
        for (Map.Entry<Point, Square> squareEntry : squareLocations.entrySet()) {
            assertTrue(board.hasSquare(squareEntry.getKey()));
            assertTrue(board.getTerrain().containsKey(squareEntry.getKey()));
        }
    }

    @Test
    void addInvalidSquare() {
        Map<Point, Square> invalidSquareLocations = Map.of(
                new Point(-1, -1), new Square(false, false),
                new Point(5, 5), new Square(false, false),
                new Point(-1, 0), new Square(false, false),
                new Point(0, -1), new Square(false, false),
                new Point(5, 4), new Square(false, false),
                new Point(4, 5), new Square(false, false));
        for (Map.Entry<Point, Square> squareEntry : invalidSquareLocations.entrySet()) {
            assertThrows(IllegalArgumentException.class, () -> board.addSquare(squareEntry.getKey(), squareEntry.getValue()));
        }
    }

    @Test
    void addPiece() {
        Map<Point, Piece> pieceLocations = new HashMap<>();
        pieceLocations.put(new Point(1, 1), new Fox(Direction.X_AXIS, new Point(1, 1)));
        for (Map.Entry<Point, Piece> pieceEntry : pieceLocations.entrySet()) {
            board.addPiece(pieceEntry.getKey(), pieceEntry.getValue());
        }
        for (Map.Entry<Point, Piece> pieceEntry : pieceLocations.entrySet()) {
            assertTrue(board.hasPiece(pieceEntry.getKey()));
        }
    }

    @Test
    void addInvalidPiece() {
        board.addSquare(new Point(0, 0), new Square(true, true));
        assertThrows(IllegalArgumentException.class, () -> board.addPiece(new Point(1, 0), new Fox(Direction.X_AXIS, new Point(1, 0))));
    }

    @Test
    void isVictory() {
        board.addPiece(new Point(0, 0), new Rabbit(new Point(0, 0)));
        board.addPiece(new Point(1, 1), new Rabbit(new Point(1, 1)));
        board.addPiece(new Point(2, 2), new Mushroom(new Point( 3, 4)));

        board.addSquare(new Point(0, 0), new Square(true, true));
        board.addSquare(new Point(1, 1), new Square(true, false));
        board.addSquare(new Point(2, 3), new Square(false, false));

    }

}