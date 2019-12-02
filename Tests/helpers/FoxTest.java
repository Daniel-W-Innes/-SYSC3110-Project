package helpers;

import model.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

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
    void testClone() {
        Piece newFox = fox.clonePiece();

        assertTrue(newFox instanceof Fox);
        assertEquals(newFox, fox);
    }

    @Test
    void testHash() {
        Fox similarFox = (Fox) fox.clonePiece();
        Fox differentFox = new Fox(Direction.Y_AXIS, new Point(0, 1));

        assertEquals(similarFox.hashCode(), fox.hashCode());
        assertNotEquals(differentFox.hashCode(), fox.hashCode());
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

        board.addSquare(new Point(0, 0), new Square(false));
        board.addSquare(new Point(4, 0), new Square(false));

        board.addPiece(fox);

        assertEquals(1, fox.getEndPoint(board, new Point(2, 0)).size());
        assertTrue(fox.getEndPoint(board, new Point(2, 0)).contains(new Point(3, 0)));
    }

    @Test
    void testGetMovesY() {
        Board board = new Board();

        Fox fox = new Fox(Direction.Y_AXIS, new Point(0, 2));

        board.addPiece(fox);

        Set<Point> expectedEndPoint = Set.of(
                new Point(0, 3),
                new Point(0, 4),
                new Point(0, 1));

        assertEquals(expectedEndPoint.size(), fox.getEndPoint(board, new Point(0, 2)).size());
        Set<Point> possibleEndPoint = fox.getEndPoint(board, new Point(0, 2));
        for (Point point : expectedEndPoint) {
            assertTrue(possibleEndPoint.contains(point));
        }
    }
}