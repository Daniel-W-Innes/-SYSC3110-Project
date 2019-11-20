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

        Graph graph = new Graph();
        graph.genSolution(board);

        Thread.sleep(250);

        while (true) {

            if (graph.getHintMove().isPresent()) {
                Move hintMove = graph.getHintMove().get();
                board.movePiece(board.getPieces().get(hintMove.getStartPoint()), hintMove.getEndPoint(), false);
            } else {
                break;
            }

            graph.advanceSolutionIndex();
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

        Graph graph = new Graph();
        graph.genSolution(board);

        Thread.sleep(2500);

        while (true) {

            if (graph.getHintMove().isPresent()) {
                Move hintMove = graph.getHintMove().get();
                board.movePiece(board.getPieces().get(hintMove.getStartPoint()), hintMove.getEndPoint(), false);
            } else {
                break;
            }

            graph.advanceSolutionIndex();
        }

        assertTrue(board.isVictory());
    }
}
