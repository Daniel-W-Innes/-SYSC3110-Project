package model;

public class Command {
    private final CommandType commandType;

    public Command(CommandType commandType) {
        this.commandType = commandType;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Command command = (Command) obj;
        return getCommandType() == command.getCommandType();
    }

    @Override
    public int hashCode() {
        return getCommandType().hashCode();
    }
}
