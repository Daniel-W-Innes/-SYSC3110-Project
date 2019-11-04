package view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;

class Tile extends JButton {
    private static final Color hole = new Color(0x52361B);
    private static final Color empty = new Color(0x009900);
    private static final Color raised = new Color(0x0000FF);
    private static final Color available = new Color(0xFFFF00);
    private boolean isHole;
    private boolean isRaised;
    private boolean isHighlighted;
    private final Point location;

    //private Piece piece;
    private final Border innerBorder;
    private final Border outerBorder;

    Tile(Point location) {
        this.location = location;
        setOpaque(true);
        setBackground(empty);
        innerBorder = BorderFactory.createLineBorder(raised, 8);
        outerBorder = BorderFactory.createLineBorder(available, 8);
    }
    
    public Point getPoint() {
        return location;
    }

    void setHole(boolean isHole) {
        this.isHole = isHole;
        updateBackgroundColor();
    }

    void reset() {
        isHole = false;
        isRaised = false;
        setIcon(null);
    }

    void setRaised(boolean isRaised) {
        this.isRaised = isRaised;
        updateBorder();
    }

    void setHighlighted(boolean isHighlighted) {
        this.isHighlighted = isHighlighted;
        updateBorder();
    }

    private void updateBorder() {
        setBorder(new CompoundBorder(isHighlighted ? outerBorder : null, isRaised ? innerBorder : null));
    }

    private void updateBackgroundColor() {
        if (isHole) {
            setBackground(hole);
        } else {
            setBackground(empty);
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