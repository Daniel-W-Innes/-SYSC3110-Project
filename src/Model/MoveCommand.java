package Model;

import java.awt.*;

public class MoveCommand extends Command {
    private final Point oldLoc;
    private final Point newLoc;

    public MoveCommand(CommandType commandType, Point oldLoc, Point newLoc) {
        super(commandType);
        this.oldLoc = oldLoc;
        this.newLoc = newLoc;
    }

    public Point getOldLoc() {
        return oldLoc;
    }

    public Point getNewLoc() {
        return newLoc;
    }
}
