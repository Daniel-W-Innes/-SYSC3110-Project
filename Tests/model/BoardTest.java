package model;


import helpers.Foxes;
import helpers.Piece;
import helpers.Rabbits;
import helpers.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class BoardTest {

    Board board = null;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @Test
    void addSquare() {

        Map<Point, Square> squareLocations = new HashMap<>();

        squareLocations.put(new Point(0, 0), new Square(true, false));
        squareLocations.put(new Point(1,1), new Square(true, true));
        squareLocations.put(new Point(4, 4), new Square(false, false));

        for(Map.Entry<Point, Square> squareEntry : squareLocations.entrySet())
        {
            board.addSquare(squareEntry.getKey(), squareEntry.getValue());
        }

        for(Map.Entry<Point, Square> squareEntry : squareLocations.entrySet())
        {
            assertTrue(board.getTerrain().containsKey(squareEntry.getKey()));
        }
    }

    @Test
    void addInvalidSquare() {
        Map<Point, Square> invalidSquareLocations = new HashMap<>();

        invalidSquareLocations.put(new Point(-1, -1), new Square(false, false));
        invalidSquareLocations.put(new Point(5, 5), new Square(false, false));
        invalidSquareLocations.put(new Point(-1, 0), new Square(false, false));
        invalidSquareLocations.put(new Point(0, -1), new Square(false, false));
        invalidSquareLocations.put(new Point(5, 4), new Square(false, false));
        invalidSquareLocations.put(new Point(4, 5), new Square(false, false));

        for(Map.Entry<Point, Square> squareEntry : invalidSquareLocations.entrySet())
        {
            try
            {
                board.addSquare(squareEntry.getKey(), squareEntry.getValue());
            }
            catch(IllegalArgumentException e)
            {
                assertTrue(true);

                continue;
            }

            fail();
        }
    }

    @Test
    void addPiece() {
        Map<Point, Piece> pieceLocations = new HashMap<>();

        pieceLocations.put(new Point(1, 1), new Foxes(Foxes.Direction.X_AXIS, new Point(1, 1)));

        for(Map.Entry<Point, Piece> pieceEntry : pieceLocations.entrySet())
        {
            board.addPiece(pieceEntry.getKey(), pieceEntry.getValue());
        }

        for(Map.Entry<Point, Piece> pieceEntry : pieceLocations.entrySet())
        {
            assertTrue(board.hasPiece(pieceEntry.getKey()));
        }
    }

    @Test
    void addInvalidPiece() {
        board.addSquare(new Point(0, 0), new Square(true, true));

        try{
            board.addPiece(new Point(1, 0), new Foxes(Foxes.Direction.X_AXIS, new Point(1, 0)));
        }
        catch(IllegalArgumentException e)
        {
            assertTrue(true);

            return;
        }

        fail();
    }

    @Test
    void hasPiece() {
    }

    @Test
    void hasSquare() {
    }

    @Test
    void testToString() {
    }

    @Test
    void isVictory() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
    }
}