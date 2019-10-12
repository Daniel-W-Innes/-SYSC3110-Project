package model;

import java.awt.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

public class MoveCommand extends Command implements Iterable<ImmutablePoint> {
    private final Set<ImmutablePoint> move;

    public MoveCommand(Point loc1, Point loc2) {
        super(CommandType.MOVE);
        move = Collections.unmodifiableSet(Set.of(new ImmutablePoint(loc1), new ImmutablePoint(loc2)));
    }

    public MoveCommand(ImmutablePoint loc1, ImmutablePoint loc2) {
        super(CommandType.MOVE);
        move = Collections.unmodifiableSet(Set.of(loc1, loc2));
    }

    @Override
    public Iterator<ImmutablePoint> iterator() {
        return move.iterator();
    }

    private Set<ImmutablePoint> getMove() {
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
        return getMove().equals(moveCommand.getMove());
    }

    @Override
    public int hashCode() {
        return getMove().hashCode();
    }
}
