package helpers;

import model.Board;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RabbitsTest {

    @Test
    void getMoves() {

        Board board = new Board();

        board.addPiece(new Point(1, 1), new Rabbits(new Point(1, 1)));
        board.addPiece(new Point(1, 3),  new Rabbits(new Point(1, 3)));
        board.addPiece(new Point(2, 2),  new Rabbits(new Point(2, 2)));
        board.addPiece(new Point(3, 2),  new Rabbits(new Point(2, 3)));
        board.addPiece(new Point(0, 1),  new Rabbits(new Point(0, 1)));

        Rabbits rabbit = new Rabbits(new Point(1, 2));

        board.addPiece(new Point(1, 2), rabbit);

        Set<Move> possibleMoves = new HashSet<>();
        possibleMoves.add(new Move(new Point(1, 2), new Point(1, 4)));
        possibleMoves.add(new Move(new Point(1, 2), new Point(4, 2)));
        possibleMoves.add(new Move(new Point(1, 2), new Point(1, 0)));

        for(int i = 0; i < possibleMoves.size(); i++) {
            assertTrue(possibleMoves.contains(rabbit.getMoves(board).get(i)));
        }
    }
}