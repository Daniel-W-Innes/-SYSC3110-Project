package helpers;

import model.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FoxesTest {

    Foxes fox = null;

    @BeforeEach
    void setUp() {
        fox = new Foxes(Foxes.Direction.X_AXIS, new Point(2, 0));
    }

    @Test
    void testFoxCreation() {
        assertEquals(new Point(2, 0), fox.getHeadLocation());
        assertEquals(Foxes.Direction.X_AXIS, fox.getDirection());
        ArrayList<Point> occupiedPoints = new ArrayList<>();
        occupiedPoints.add(new Point(2, 0));
        occupiedPoints.add(new Point(1, 0));

        testBoardUsed(fox, occupiedPoints);
    }

    @Test
    void testUpdateBoardSpotsUsed() {
        fox.updateBoardSpotUsed(new Point(3, 0));
        assertEquals(Foxes.Direction.X_AXIS, fox.getDirection());
        ArrayList<Point> occupiedPoints = new ArrayList<>();
        occupiedPoints.add(new Point(3, 0));
        occupiedPoints.add(new Point(2, 0));

        testBoardUsed(fox, occupiedPoints);

    }

    private void testBoardUsed(Foxes fox, ArrayList<Point> expectedPoints) {
        assertEquals(expectedPoints.size(), fox.boardSpotsUsed().size());
        for(Point occupiedPoint : expectedPoints) {
            assertTrue(fox.boardSpotsUsed().contains(occupiedPoint));
        }
    }

    @Test
    void testGetIcon() {
        assertEquals(Foxes.horizontalHeadImageLocation, fox.getImageIcon(new Point(2, 0)).getDescription());
        assertEquals(Foxes.horizontalTailImageLocation, fox.getImageIcon(new Point(1, 0)).getDescription());

        fox = new Foxes(Foxes.Direction.Y_AXIS, new Point(0, 2));

        assertEquals(Foxes.vertexHeadImageLocation, fox.getImageIcon(new Point(0, 2)).getDescription());
        assertEquals(Foxes.verticalTailImageLocation, fox.getImageIcon(new Point(0, 1)).getDescription());
    }

    @Test
    void testGetMovesX() {
        Board board = new Board();

        board.addSquare(new Point(0, 0), new Square(false, false));
        board.addSquare(new Point(4, 0), new Square(false, false));

        board.addPiece(new Point(2, 0), fox);

        assertEquals(1, fox.getMoves(board, new Point(2, 0)).size());
        assertEquals(new Move(new Point(2, 0), new Point(3, 0)), fox.getMoves(board, new Point(2, 0)).get(0));
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

        assertEquals(possibleMoves.size(), fox.getMoves(board, new Point(0, 2)).size());

        for(int i = 0; i < possibleMoves.size(); i++) {
            assertTrue(possibleMoves.contains(fox.getMoves(board, new Point(0, 2)).get(i)));
        }
    }
}