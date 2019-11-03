package view;

import helpers.Piece;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;

public class BoardTile extends JButton {
    private Point p;
    private boolean isHole;
    private boolean isRaised;
    private boolean isHighlighted;

    private Border innerBorder;
    private Border outerBorder;
    private JLayeredPane layeredPane;

    private Piece piece;

    private static Color brown = new Color(51, 0, 25);
    private static Color green = new Color(0, 153, 0);

    public BoardTile(Point p) {
        this.p = p;

        this.setOpaque(true);
        this.setBackground(BoardTile.green);

        //hack
        super.setIcon(new ImageIcon("./resources/Rabbit_white.png"));

        this.innerBorder = BorderFactory.createLineBorder(Color.darkGray, 8);
        this.outerBorder = BorderFactory.createLineBorder(Color.yellow, 8);
    }

    public Point getPoint() {
        return this.p;
    }

    public void setHole(boolean b) {
        this.isHole = b;
        updateBackgroundColor();
    }

    public void setRaised(boolean b) {
        this.isRaised = b;
        this.updateBorder();
    }

    public void setHighlighted(boolean b) {
        this.isHighlighted = b;
        this.updateBorder();
    }

    public void updateBorder() {
        this.setBorder(new CompoundBorder(this.isHighlighted ? this.outerBorder : null,
                this.isRaised ? this.innerBorder : null));
    }
    public void updateBackgroundColor() {
        if(this.isHole) {
            this.setBackground(BoardTile.brown);
        } else {
            this.setBackground(BoardTile.green);
        }
    }

    public void placePiece(Piece piece) {
        this.piece = piece;
    }

    public void removePiece() {
        this.piece = null;
    }
}
