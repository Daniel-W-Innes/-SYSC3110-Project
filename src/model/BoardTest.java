package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


class BoardTest {
    private Board board;
    private Board boardSame;
    private Board boardDifferentPiece;
    private Board boardDifferentRaised;

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
                .addTunnel(new ImmutablePoint(0, 0))
                .addTunnel(new ImmutablePoint(4, 4))
                .addTunnel(new ImmutablePoint(0, 4))
                .addTunnel(new ImmutablePoint(4, 0))
                .addTunnel(new ImmutablePoint(2, 2))

                .addRaisedSquare(new ImmutablePoint(0, 2))
                .addRaisedSquare(new ImmutablePoint(2, 0))
                .addRaisedSquare(new ImmutablePoint(2, 4))
                .addRaisedSquare(new ImmutablePoint(4, 2))

                .addPieces(new ImmutablePoint(1, 4), Piece.RABBIT)
                .addPieces(new ImmutablePoint(4, 2), Piece.RABBIT)
                .addPieces(new ImmutablePoint(3, 0), Piece.RABBIT)

                .addPieces(new ImmutablePoint(2, 4), Piece.MUSHROOM)
                .addPieces(new ImmutablePoint(3, 1), Piece.MUSHROOM)

                .addPieces(new ImmutablePoint(1, 1), Piece.FOX)
                .addPieces(new ImmutablePoint(1, 0), Piece.FOX)
                .addPieces(new ImmutablePoint(4, 3), Piece.FOX)
                .addPieces(new ImmutablePoint(3, 3), Piece.FOX)
                .build();

        boardSame = new Board.Builder()
                .addTunnel(new ImmutablePoint(0, 0))
                .addTunnel(new ImmutablePoint(4, 4))
                .addTunnel(new ImmutablePoint(0, 4))
                .addTunnel(new ImmutablePoint(4, 0))
                .addTunnel(new ImmutablePoint(2, 2))

                .addRaisedSquare(new ImmutablePoint(0, 2))
                .addRaisedSquare(new ImmutablePoint(2, 0))
                .addRaisedSquare(new ImmutablePoint(2, 4))
                .addRaisedSquare(new ImmutablePoint(4, 2))

                .addPieces(new ImmutablePoint(1, 4), Piece.RABBIT)
                .addPieces(new ImmutablePoint(4, 2), Piece.RABBIT)
                .addPieces(new ImmutablePoint(3, 0), Piece.RABBIT)

                .addPieces(new ImmutablePoint(2, 4), Piece.MUSHROOM)
                .addPieces(new ImmutablePoint(3, 1), Piece.MUSHROOM)

                .addPieces(new ImmutablePoint(1, 1), Piece.FOX)
                .addPieces(new ImmutablePoint(1, 0), Piece.FOX)
                .addPieces(new ImmutablePoint(4, 3), Piece.FOX)
                .addPieces(new ImmutablePoint(3, 3), Piece.FOX)
                .build();

        boardDifferentPiece = new Board.Builder()
                .addTunnel(new ImmutablePoint(0, 0))
                .addTunnel(new ImmutablePoint(4, 4))
                .addTunnel(new ImmutablePoint(0, 4))
                .addTunnel(new ImmutablePoint(4, 0))
                .addTunnel(new ImmutablePoint(2, 2))

                .addRaisedSquare(new ImmutablePoint(0, 2))
                .addRaisedSquare(new ImmutablePoint(2, 0))
                .addRaisedSquare(new ImmutablePoint(2, 4))
                .addRaisedSquare(new ImmutablePoint(4, 2))

                .addPieces(new ImmutablePoint(1, 4), Piece.MUSHROOM)
                .addPieces(new ImmutablePoint(4, 2), Piece.RABBIT)
                .addPieces(new ImmutablePoint(3, 0), Piece.RABBIT)

                .addPieces(new ImmutablePoint(2, 4), Piece.MUSHROOM)
                .addPieces(new ImmutablePoint(3, 1), Piece.MUSHROOM)

                .addPieces(new ImmutablePoint(1, 1), Piece.FOX)
                .addPieces(new ImmutablePoint(1, 0), Piece.FOX)
                .addPieces(new ImmutablePoint(4, 3), Piece.FOX)
                .addPieces(new ImmutablePoint(3, 3), Piece.FOX)
                .build();

        boardDifferentRaised = new Board.Builder()
                .addTunnel(new ImmutablePoint(0, 0))
                .addTunnel(new ImmutablePoint(4, 4))
                .addTunnel(new ImmutablePoint(0, 4))
                .addTunnel(new ImmutablePoint(4, 0))
                .addTunnel(new ImmutablePoint(2, 2))

                .addRaisedSquare(new ImmutablePoint(0, 2))
                .addRaisedSquare(new ImmutablePoint(2, 1))
                .addRaisedSquare(new ImmutablePoint(2, 4))
                .addRaisedSquare(new ImmutablePoint(4, 2))

                .addPieces(new ImmutablePoint(1, 4), Piece.RABBIT)
                .addPieces(new ImmutablePoint(4, 2), Piece.RABBIT)
                .addPieces(new ImmutablePoint(3, 0), Piece.RABBIT)

                .addPieces(new ImmutablePoint(2, 4), Piece.MUSHROOM)
                .addPieces(new ImmutablePoint(3, 1), Piece.MUSHROOM)

                .addPieces(new ImmutablePoint(1, 1), Piece.FOX)
                .addPieces(new ImmutablePoint(1, 0), Piece.FOX)
                .addPieces(new ImmutablePoint(4, 3), Piece.FOX)
                .addPieces(new ImmutablePoint(3, 3), Piece.FOX)
                .build();
    }
}