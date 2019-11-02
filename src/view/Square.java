package view;

import javax.swing.*;
import java.awt.*;

/**
 * Models a square in the board (View)
 */
public class Square extends JButton {
    private Point p;

    public Square(Point p, ImageIcon icon){
        this.p = p;
        super.setIcon(icon);
        //this.setText(p.toString());
    }

    public Point getPoint(){
        return p;
    }

    //Dumb method that sets the icon
    public void setIcon(ImageIcon icon) {
        super.setIcon(icon);
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
