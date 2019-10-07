package View;

import Model.Board;

public class TextBased implements ViewApi {
    @Override
    public void draw(Board board) {
        System.out.println(board.toString());
    }
}
