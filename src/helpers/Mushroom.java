package helpers;

import model.Board;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mushroom implements Piece {
    //The piece knows it's own image
    private static final ImageIcon icon = new ImageIcon("./resources/Mushroom.jpg");

    @Override
    public ImageIcon getIcon() {
        return icon;
    }

    @Override
    public Map<Move, List<Move>> getMoves(Board board, Point point) {
        return new HashMap<>();
    }

    @Override
    public String toString() {
        return "M";
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj != null && obj.getClass() == this.getClass();
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
