package helpers;

import model.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FoxTest {

    private Fox fox;

    private static void testBoardUsed(Fox fox, List<Point> expectedPoints) {
        assertEquals(expectedPoints.size(), fox.boardSpotsUsed().size());
        for (Point occupiedPoint : expectedPoints) {
            assertTrue(fox.boardSpotsUsed().contains(occupiedPoint));
        }
    }

    @BeforeEach
    void setUp() {
        fox = new Fox(Direction.X_AXIS, new Point(2, 0));
    }

    @Test
    void testFoxCreation() {
        assertEquals(new Point(2, 0), fox.getHeadLocation());
        assertEquals(Direction.X_AXIS, fox.getDirection());
        List<Point> occupiedPoints = new ArrayList<>();
        occupiedPoints.add(new Point(2, 0));
        occupiedPoints.add(new Point(1, 0));

        testBoardUsed(fox, occupiedPoints);
    }

    @Test
    void testUpdateBoardSpotsUsed() {
        fox.updateBoardSpotUsed(new Point(3, 0));
        assertEquals(Direction.X_AXIS, fox.getDirection());
        List<Point> occupiedPoints = new ArrayList<>();
        occupiedPoints.add(new Point(3, 0));
        occupiedPoints.add(new Point(2, 0));

        testBoardUsed(fox, occupiedPoints);

    }

    @Test
    void testGetIcon() {
        assertEquals(Fox.vertexHeadImageLocation, fox.getImageIcon(new Point(2, 0)).getDescription());
        assertEquals(Fox.verticalTailImageLocation, fox.getImageIcon(new Point(1, 0)).getDescription());

        fox = new Fox(Direction.Y_AXIS, new Point(0, 2));

        assertEquals(Fox.horizontalHeadImageLocation, fox.getImageIcon(new Point(0, 2)).getDescription());
        assertEquals(Fox.horizontalTailImageLocation, fox.getImageIcon(new Point(0, 1)).getDescription());
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

        Fox fox = new Fox(Direction.Y_AXIS, new Point(0, 2));

        board.addPiece(new Point(0, 2), fox);

        Set<Move> possibleMoves = Set.of(
                new Move(new Point(0, 2), new Point(0, 3)),
                new Move(new Point(0, 2), new Point(0, 4)),
                new Move(new Point(0, 2), new Point(0, 1)));

        assertEquals(possibleMoves.size(), fox.getMoves(board, new Point(0, 2)).size());

        for(int i = 0; i < possibleMoves.size(); i++) {
            assertTrue(possibleMoves.contains(fox.getMoves(board, new Point(0, 2)).get(i)));
        }
    }
}