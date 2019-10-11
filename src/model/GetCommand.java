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
}
