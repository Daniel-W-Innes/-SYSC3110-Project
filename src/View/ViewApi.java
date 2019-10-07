package View;

import Model.Board;
import Model.Command;

public interface ViewApi {
    void draw(Board board);
    Command getCommand();
}
