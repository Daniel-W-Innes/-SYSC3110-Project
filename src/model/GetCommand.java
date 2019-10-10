package model;

import java.awt.*;

public class GetCommand extends Command {
    private final Point loc;

    public GetCommand(CommandType commandType, Point loc) {
        super(commandType);
        this.loc = loc;
    }

    public Point getLoc() {
        return loc;
    }
}
