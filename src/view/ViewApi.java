package view;

import controller.GraphManager;
import model.Command;

public interface ViewApi {
    void setGraphManager(GraphManager graphManager);

    void drawBoard();

    Command getCommand();

    void drawMessage(String message);
}
