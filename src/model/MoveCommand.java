package model;

import java.awt.*;
import java.util.Iterator;
import java.util.Set;

public class MoveCommand extends Command implements Iterable<Point> {
    private final Set<Point> move;

    public MoveCommand(Point oldLoc, Point newLoc) {
        super(CommandType.MOVE);
        move = Set.of(oldLoc, newLoc);
    }

    @Override
    public Iterator<Point> iterator() {
        return move.iterator();
    }
}
