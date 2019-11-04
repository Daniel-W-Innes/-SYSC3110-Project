package helpers;

import model.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RabbitsTest {

    Rabbits rabbit = null;

    @BeforeEach
    void setUp() {
        rabbit = new Rabbits(new Point(1, 2));
    }

    @Test
    void testGetMoves() {

        Board board = new Board();

        board.addPiece(new Point(1, 1), new Rabbits(new Point(1, 1)));
        board.addPiece(new Point(1, 3),  new Rabbits(new Point(1, 3)));
        board.addPiece(new Point(2, 2),  new Rabbits(new Point(2, 2)));
        board.addPiece(new Point(3, 2),  new Rabbits(new Point(3, 2)));
        board.addPiece(new Point(0, 1),  new Rabbits(new Point(0, 1)));

        rabbit = new Rabbits(new Point(1, 2));

        board.addPiece(new Point(1, 2), rabbit);

        Set<Move> possibleMoves = new HashSet<>();
        possibleMoves.add(new Move(new Point(1, 2), new Point(1, 4)));
        possibleMoves.add(new Move(new Point(1, 2), new Point(4, 2)));
        possibleMoves.add(new Move(new Point(1, 2), new Point(1, 0)));

        for(int i = 0; i < possibleMoves.size(); i++) {
            assertTrue(possibleMoves.contains(rabbit.getMoves(board, new Point(1, 2)).get(i)));
        }
    }

    @Test
    void testBoardSpotsUsed() {
        assertEquals(1, rabbit.boardSpotsUsed().size());
        assertTrue(rabbit.boardSpotsUsed().contains(new Point(1, 2)));
    }

    @Test
    void testUpdateBoardSpotsUsed() {
        rabbit.updateBoardSpotUsed(new Point(2, 3));
        assertEquals(1, rabbit.boardSpotsUsed().size());
        assertTrue(rabbit.boardSpotsUsed().contains(new Point(2, 3)));
    }

    @Test
    void testGetImageIcon() {
        assertEquals(Rabbits.imageIconLocation, rabbit.getImageIcon(new Point()).getDescription());
    }
}