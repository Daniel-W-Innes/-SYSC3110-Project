package model;

import java.awt.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

public class MoveCommand extends Command implements Iterable<Point> {
    private final Set<Point> move;

    public MoveCommand(Point oldLoc, Point newLoc) {
        super(CommandType.MOVE);
        move = Collections.unmodifiableSet(Set.of(oldLoc, newLoc));
    }

    @Override
    public Iterator<Point> iterator() {
        return move.iterator();
    }

    private Set<Point> getMove() {
        return move;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        MoveCommand moveCommand = (MoveCommand) obj;
        return move.equals(moveCommand.getMove());
    }

    @Override
    public int hashCode() {
        return move.hashCode();
    }
}
