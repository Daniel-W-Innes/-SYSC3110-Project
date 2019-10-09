package View;

import Controller.BoardManager;
import Model.Command;

public interface ViewApi {
    void setBoardManager(BoardManager boardManager);

    void drawBoard();

    Command getCommand();

    void drawMessage(String message);
}
