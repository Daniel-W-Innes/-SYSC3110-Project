package backend.helpers;

import backend.models.Level;
import frontend.View;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

class GameBuilder {

    private static ImmutableBoard getStartingBoard(int levelNumber) {
        MutableBoard mutableBoard;
        switch (levelNumber) {
            case 1:
                mutableBoard = new MutableBoard.Builder(true).buildMutableBoard();
                mutableBoard.addPiece(new Rabbit(new Color(0xCD853F), new Point(2, 3)));
                mutableBoard.addPiece(new Mushroom(new Point(0, 1)));
                mutableBoard.addPiece(new Mushroom(new Point(0, 2)));
                mutableBoard.addPiece(new Mushroom(new Point(1, 3)));
                break;
            case 20:
                mutableBoard = new MutableBoard.Builder(true).buildMutableBoard();
                mutableBoard.addPiece(new Rabbit(new Color(0xCD853F), new Point(1, 4)));
                mutableBoard.addPiece(new Rabbit(new Color(0x808080), new Point(4, 2)));
                mutableBoard.addPiece(new Rabbit(new Color(0xFFFFFF), new Point(3, 0)));
                mutableBoard.addPiece(new Mushroom(new Point(2, 4)));
                mutableBoard.addPiece(new Mushroom(new Point(3, 1)));
                mutableBoard.addPiece(new Fox(new Point(1, 1), new Point(1, 0)));
                mutableBoard.addPiece(new Fox(new Point(4, 3), new Point(3, 3)));
                break;
            case 60:
                mutableBoard = new MutableBoard.Builder(true).buildMutableBoard();
                mutableBoard.addPiece(new Rabbit(new Color(0xCD853F), new Point(1, 3)));
                mutableBoard.addPiece(new Rabbit(new Color(0x808080), new Point(2, 4)));
                mutableBoard.addPiece(new Rabbit(new Color(0xFFFFFF), new Point(4, 3)));
                mutableBoard.addPiece(new Mushroom(new Point(0, 3)));
                mutableBoard.addPiece(new Mushroom(new Point(2, 2)));
                mutableBoard.addPiece(new Mushroom(new Point(3, 1)));
                mutableBoard.addPiece(new Fox(new Point(1, 1), new Point(1, 0)));
                mutableBoard.addPiece(new Fox(new Point(1, 1), new Point(1, 0)));
                break;
            default:
                mutableBoard = new MutableBoard.Builder(true).buildMutableBoard();
                break;

        }
        return mutableBoard.getImmutableBoard();
    }

    static Map<Integer, Level> buildLevel(View view) {
        Map<Integer, Level> levels = new HashMap<>();
        levels.put(1, new Level.Builder(getStartingBoard(1)).build());
        levels.put(20, new Level.Builder(getStartingBoard(20)).build());
        levels.put(60, new Level.Builder(getStartingBoard(60)).build());
        levels.values().forEach(level -> level.addView(view));
        return levels;
    }
}
