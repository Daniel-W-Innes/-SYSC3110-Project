package view;

import model.Board;

public interface Observer {
    void update(Board board);
}
