package view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;

class BoardTile extends JButton {
    private static final Color brown = new Color(82, 54, 27);
    private boolean isHole;
    private boolean isRaised;
    private boolean isHighlighted;
    private static final Color green = new Color(0, 153, 0);
    private final Point p;

    //private Piece piece;
    private final Border innerBorder;
    private final Border outerBorder;

    BoardTile(Point p) {
        this.p = p;

        this.setOpaque(true);
        this.setBackground(BoardTile.green);

        //test
        //super.setIcon(new ImageIcon("./resources/Rabbit_white.png"));

        this.innerBorder = BorderFactory.createLineBorder(Color.blue, 8);
        this.outerBorder = BorderFactory.createLineBorder(Color.yellow, 8);
    }
    
    public Point getPoint() {
        return this.p;
    }

    public void setHole(boolean b) {
        this.isHole = b;
        updateBackgroundColor();
    }

    void setRaised(boolean b) {
        this.isRaised = b;
        this.updateBorder();
    }

    void setHighlighted(boolean b) {
        this.isHighlighted = b;
        this.updateBorder();
    }

    private void updateBorder() {
        this.setBorder(new CompoundBorder(this.isHighlighted ? this.outerBorder : null,
                this.isRaised ? this.innerBorder : null));
    }

    private void updateBackgroundColor() {
        if(this.isHole) {
            this.setBackground(BoardTile.brown);
        } else {
            this.setBackground(BoardTile.green);
        }
    }

//    public void placePiece(Piece piece) {
//        //this.piece = piece;
//        this.setIcon(piece.getImageIcon());
//    }

//    public void removePiece() {
//        //this.piece = null;
//        this.setIcon(null);
//    }
}