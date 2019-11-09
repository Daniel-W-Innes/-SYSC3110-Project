package backend.helpers;

import backend.models.Level;
import frontend.View;
import frontend.something;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


class Game {
    private final Map<Integer, Level> levels;
    private int levelNumber;

    private Game() {
        levels = new HashMap<>();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.setUp(new something());
    }

    public boolean move(Point start, Point end) {
        return levels.get(levelNumber).move(start, end);
    }

    public boolean changeLevel(int levelNumber) {
        if (getLevels().containsKey(levelNumber)) {
            this.levelNumber = levelNumber;
            return true;
        } else {
            return false;
        }
    }

    public Set<Move> getMoves(Point point) {
        return levels.get(levelNumber).getMoves(point);
    }

    public void setUp(View view) {
        levelNumber = 1;
        levels.putAll(GameBuilder.buildLevel(view));
    }

    private Map<Integer, Level> getLevels() {
        return levels;
    }
}
