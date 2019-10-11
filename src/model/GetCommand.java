package model;

import java.awt.*;

public class GetCommand extends Command {
    private final Point loc;

    public GetCommand(Point loc) {
        super(CommandType.GET);
        this.loc = loc;
    }

    public Point getLoc() {
        return loc;
    }
}
