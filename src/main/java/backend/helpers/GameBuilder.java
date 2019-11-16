package backend.helpers;

import backend.models.Level;
import frontend.View;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

final class GameBuilder {
    private static Board getStartingBoard(int levelNumber) {
        switch (levelNumber) {
            case 1:
                return new Board.Builder(true)
                        .addPiece(new Rabbit(new Color(0xCD853F), new Point(2, 3)))
                        .addPiece(new Mushroom(new Point(0, 1)))
                        .addPiece(new Mushroom(new Point(0, 2)))
                        .addPiece(new Mushroom(new Point(1, 3)))
                        .build();
            case 20:
                return new Board.Builder(true)
                        .addPiece(new Rabbit(new Color(0xCD853F), new Point(1, 4)))
                        .addPiece(new Rabbit(new Color(0x808080), new Point(4, 2)))
                        .addPiece(new Rabbit(new Color(0xFFFFFF), new Point(3, 0)))
                        .addPiece(new Mushroom(new Point(2, 4)))
                        .addPiece(new Mushroom(new Point(3, 1)))
                        .addPiece(new Fox(new Point(1, 1), new Point(1, 0)))
                        .addPiece(new Fox(new Point(4, 3), new Point(3, 3)))
                        .build();
            case 60:
                return new Board.Builder(true)
                        .addPiece(new Rabbit(new Color(0xCD853F), new Point(1, 3)))
                        .addPiece(new Rabbit(new Color(0x808080), new Point(2, 4)))
                        .addPiece(new Rabbit(new Color(0xFFFFFF), new Point(4, 3)))
                        .addPiece(new Mushroom(new Point(0, 3)))
                        .addPiece(new Mushroom(new Point(2, 2)))
                        .addPiece(new Mushroom(new Point(3, 1)))
                        .addPiece(new Fox(new Point(1, 1), new Point(1, 0)))
                        .addPiece(new Fox(new Point(1, 1), new Point(1, 0)))
                        .build();
            default:
                return new Board.Builder(true).build();
        }
    }

    static Map<Integer, Level> buildLevel(View view) {
        Map<Integer, Level> levels = new HashMap<>();
        //levels.put(1, new Level.Builder(getStartingBoard(1)).build());
        Level.Tree tree = new Level.Builder(getStartingBoard(20)).build();
        Level.TreeNode curr = tree.solution;
        while(curr.parent != null) {
            System.out.println(curr.move);
            curr = curr.parent;
        }
        Level.Tree tree2 = new Level.Builder(getStartingBoard(60)).build();
        Level.TreeNode curr2 = tree2.solution;
        while(curr2.parent != null) {
            System.out.println(curr2.move);
            curr2 = curr2.parent;
        }
        //levels.put(20, new Level.Builder(getStartingBoard(20)).build());
        //levels.put(60, new Level.Builder(getStartingBoard(60)).build());
        levels.values().forEach(level -> level.addView(view));
        return levels;
    }
}
