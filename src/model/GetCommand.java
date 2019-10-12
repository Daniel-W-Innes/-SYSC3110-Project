package model;

import java.awt.*;

public class GetCommand extends Command {
    private final ImmutablePoint loc;

    public GetCommand(Point loc) {
        super(CommandType.GET);
        this.loc = new ImmutablePoint(loc);
    }

    public ImmutablePoint getLoc() {
        return loc;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        GetCommand getCommand = (GetCommand) obj;
        return getLoc() == getCommand.getLoc();
    }

    @Override
    public int hashCode() {
        return getLoc().hashCode();
    }
}
