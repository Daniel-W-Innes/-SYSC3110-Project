package helpers;

import model.Board;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static helpers.GameBuilder.getStartingBoard;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class GraphTest {

    @Test
    void testLevelOneSolution() throws InterruptedException {
        Board board = null;
        try {
            board = getStartingBoard("1");
        } catch (IOException e) {
            fail();
        }

        Graph solution = new Graph(board);

        Thread.sleep(250);

        while (true) {

            if (solution.getHintMove().isPresent()) {
                Move hintMove = solution.getHintMove().get();
                board.movePiece(board.getPieces().get(hintMove.getStartPoint()), hintMove.getEndPoint(), false);
            } else {
                break;
            }

            solution.advanceSolutionIndex();
        }

        assertTrue(board.isVictory());
    }

    @Test
    void testLevel20Solution() throws InterruptedException {
        Board board = null;
        try {
            board = getStartingBoard("20");
        } catch (IOException e) {
            fail();
        }

        Graph solution = new Graph(board);

        Thread.sleep(2500);

        while (true) {

            if (solution.getHintMove().isPresent()) {
                Move hintMove = solution.getHintMove().get();
                board.movePiece(board.getPieces().get(hintMove.getStartPoint()), hintMove.getEndPoint(), false);
            } else {
                break;
            }

            solution.advanceSolutionIndex();
        }

        assertTrue(board.isVictory());
    }
}
