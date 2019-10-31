package view;

import javax.swing.*;
import java.awt.*;

public class Square extends JButton {
    private Point p;

    public Square(Point p){
        this.p = p;
        setText(p.toString());
    }

    public Point getPoint(){
        return p;
    }

    @Override
    public String toString() {
        return "Square at " + p.x + ", " + p.y;
    }

    @Override
    public int hashCode(){
        return p.hashCode();
    }
}
