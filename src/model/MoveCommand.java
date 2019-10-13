package model;

import java.awt.*;
import java.util.Arrays;

public class MoveCommand {
    private final Point from;
    private final Point to;
    private final int hashcode;

    public MoveCommand(Point from, Point to) {
        this.from = from;
        this.to = to;
        hashcode = Arrays.hashCode((new int[]{from.hashCode(), to.hashCode()}));
    }

    public Point getFrom() {
        return from;
    }

    public Point getTo() {
        return to;
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
        return getFrom().equals(moveCommand.getFrom()) && getTo().equals(moveCommand.getTo());
    }

    @Override
    public int hashCode() {
        return hashcode;
    }
}
