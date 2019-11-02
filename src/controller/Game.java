package controller;

import helpers.Level;
import helpers.Move;
import view.Frame;
import view.View;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static helpers.GameBuilder.buildLevel;
import static helpers.GameBuilder.getStartingBoard;

public class Game {
    private final Map<Integer, Level> levels;
    private int levelNumber;

    private Game() {
        levels = new HashMap<>();
    }

    public static void main(String[] args) {
        Game game = new Game();
        Frame frame = new Frame();
        game.setUp(frame.getBoard());
    }

    public boolean move(Move move) {
        return levels.get(levelNumber).move(move);
    }

    public boolean changeLevel(int levelNumber) {
        if (getLevels().containsKey(levelNumber)) {
            this.levelNumber = levelNumber;
            return true;
        } else {
            return false;
        }
    }

    public List<Move> getMoves(Point point) {
        return levels.get(levelNumber).getMove(point);
    }

    public void resetLevel() {
        getLevels().get(levelNumber).resetPieces(getStartingBoard(levelNumber).getPieces());
    }

    public void setUp(View view) {
        levelNumber = 1;
        levels.putAll(buildLevel(view));
    }
    private Map<Integer, Level> getLevels() {
        return levels;
    }
}
