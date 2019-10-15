package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


class BoardTest {
    private Board board;
    private Board boardSame;
    private Board boardDifferentPiece;
    private Board boardDifferentRaised;

    @Test
    void testSetContains() {
        Set<Board> visitedBoards = new HashSet<>();
        visitedBoards.add(board);
        assertTrue(visitedBoards.contains(boardSame));
        assertFalse(visitedBoards.contains(boardDifferentPiece));
        visitedBoards.add(boardDifferentPiece);
        assertTrue(visitedBoards.contains(boardDifferentPiece));
        assertFalse(visitedBoards.contains(boardDifferentRaised));
    }

    @Test
    void testHashCode() {
        assertEquals(board.hashCode(), board.hashCode());
        assertEquals(board.hashCode(), boardSame.hashCode());
        assertNotEquals(board.hashCode(), boardDifferentPiece.hashCode());
        assertNotEquals(board.hashCode(), boardDifferentRaised.hashCode());
    }

    @Test
    void testEquals() {
        assertEquals(board, board);
        assertEquals(board, boardSame);
        assertNotEquals(board, boardDifferentPiece);
        assertNotEquals(board, boardDifferentRaised);
    }

    @BeforeEach
    void setUp() {
        board = new Board.Builder()
                .addHole(new Point(0, 0))
                .addHole(new Point(4, 4))
                .addHole(new Point(0, 4))
                .addHole(new Point(4, 0))
                .addHole(new Point(2, 2))

                .addRaisedSquare(new Point(0, 2))
                .addRaisedSquare(new Point(2, 0))
                .addRaisedSquare(new Point(2, 4))
                .addRaisedSquare(new Point(4, 2))

                .addPieces(new Point(1, 4), Piece.RABBIT)
                .addPieces(new Point(4, 2), Piece.RABBIT)
                .addPieces(new Point(3, 0), Piece.RABBIT)

                .addPieces(new Point(2, 4), Piece.MUSHROOM)
                .addPieces(new Point(3, 1), Piece.MUSHROOM)

                .addPieces(new Point(1, 1), Piece.FOX_PLUS_Y)
                .addPieces(new Point(1, 0), Piece.FOX_MINUS_Y)
                .addPieces(new Point(4, 3), Piece.FOX_PLUS_X)
                .addPieces(new Point(3, 3), Piece.FOX_MINUS_X)
                .build();

        boardSame = new Board.Builder()
                .addHole(new Point(0, 0))
                .addHole(new Point(4, 4))
                .addHole(new Point(0, 4))
                .addHole(new Point(4, 0))
                .addHole(new Point(2, 2))

                .addRaisedSquare(new Point(0, 2))
                .addRaisedSquare(new Point(2, 0))
                .addRaisedSquare(new Point(2, 4))
                .addRaisedSquare(new Point(4, 2))

                .addPieces(new Point(1, 4), Piece.RABBIT)
                .addPieces(new Point(4, 2), Piece.RABBIT)
                .addPieces(new Point(3, 0), Piece.RABBIT)

                .addPieces(new Point(2, 4), Piece.MUSHROOM)
                .addPieces(new Point(3, 1), Piece.MUSHROOM)

                .addPieces(new Point(1, 1), Piece.FOX_PLUS_Y)
                .addPieces(new Point(1, 0), Piece.FOX_MINUS_Y)
                .addPieces(new Point(4, 3), Piece.FOX_PLUS_X)
                .addPieces(new Point(3, 3), Piece.FOX_MINUS_X)
                .build();

        boardDifferentPiece = new Board.Builder()
                .addHole(new Point(0, 0))
                .addHole(new Point(4, 4))
                .addHole(new Point(0, 4))
                .addHole(new Point(4, 0))
                .addHole(new Point(2, 2))

                .addRaisedSquare(new Point(0, 2))
                .addRaisedSquare(new Point(2, 0))
                .addRaisedSquare(new Point(2, 4))
                .addRaisedSquare(new Point(4, 2))

                .addPieces(new Point(1, 4), Piece.MUSHROOM)
                .addPieces(new Point(4, 2), Piece.RABBIT)
                .addPieces(new Point(3, 0), Piece.RABBIT)

                .addPieces(new Point(2, 4), Piece.MUSHROOM)
                .addPieces(new Point(3, 1), Piece.MUSHROOM)

                .addPieces(new Point(1, 1), Piece.FOX_PLUS_Y)
                .addPieces(new Point(1, 0), Piece.FOX_MINUS_Y)
                .addPieces(new Point(4, 3), Piece.FOX_PLUS_X)
                .addPieces(new Point(3, 3), Piece.FOX_MINUS_X)
                .build();

        boardDifferentRaised = new Board.Builder()
                .addHole(new Point(0, 0))
                .addHole(new Point(4, 4))
                .addHole(new Point(0, 4))
                .addHole(new Point(4, 0))
                .addHole(new Point(2, 2))

                .addRaisedSquare(new Point(0, 2))
                .addRaisedSquare(new Point(2, 1))
                .addRaisedSquare(new Point(2, 4))
                .addRaisedSquare(new Point(4, 2))

                .addPieces(new Point(1, 4), Piece.RABBIT)
                .addPieces(new Point(4, 2), Piece.RABBIT)
                .addPieces(new Point(3, 0), Piece.RABBIT)

                .addPieces(new Point(2, 4), Piece.MUSHROOM)
                .addPieces(new Point(3, 1), Piece.MUSHROOM)

                .addPieces(new Point(1, 1), Piece.FOX_PLUS_Y)
                .addPieces(new Point(1, 0), Piece.FOX_MINUS_Y)
                .addPieces(new Point(4, 3), Piece.FOX_PLUS_X)
                .addPieces(new Point(3, 3), Piece.FOX_MINUS_X)
                .build();
    }
}