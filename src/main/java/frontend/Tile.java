package frontend;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;

/**
 * Class representing a button for the game, which may correspond to a game piece or square.
 */

class Tile extends JButton {
    private static final Color hole = new Color(0x52361B);
    private static final Color empty = new Color(0x009900);
    private static final Color raised = new Color(0x0000FF);
    private static final Color available = new Color(0xFFFF00);
    private boolean isHole;
    private boolean isRaised;
    private boolean isHighlighted;
    private final Point location;

    private final Border innerBorder;
    private final Border outerBorder;

    /**
     * Initializes the tile to correspond with the location passed in.
     *
     * @param location the point the tile should represent
     */

    Tile(Point location) {
        this.location = location;
        setOpaque(true);
        setBackground(empty);
        innerBorder = BorderFactory.createLineBorder(raised, 8);
        outerBorder = BorderFactory.createLineBorder(available, 8);
    }

    /**
     * Get the point that the tile represents.
     *
     * @return the point the tile represents
     */

    public Point getPoint() {
        return location;
    }

    /**
     * Set the current tile to represent a square with a hole.
     *
     * @param isHole true if there is a hole on the point represented by the tile; otherwise false
     */

    void setHole(boolean isHole) {
        this.isHole = isHole;
        updateBackgroundColor();
    }

    /**
     *  Reset the tile to a default state.
     */

    void reset() {
        isHole = false;
        isRaised = false;
        setIcon(null);
    }

    /**
     * Set the current tile to represent a raised square.
     *
     * @param isRaised true if there is a raised square on the point represented by the tile; otherwise false
     */

    void setRaised(boolean isRaised) {
        this.isRaised = isRaised;
        updateBorder();
    }

    /**
     * Sets the tile as highlighted if there is a valid move to the point represented by the tile.
     *
     * @param isHighlighted true if the tile should be highlighted
     */

    void setHighlighted(boolean isHighlighted) {
        this.isHighlighted = isHighlighted;
        updateBorder();
    }

    /**
     *  Highlight the border of the tile based off of the properties of the tile.
     */

    private void updateBorder() {
        setBorder(new CompoundBorder(isHighlighted ? outerBorder : null, isRaised ? innerBorder : null));
    }

    /**
     * Colour the tile based off of whether the point it represents has a hole.
     */

    private void updateBackgroundColor() {
        if (isHole) {
            setBackground(hole);
        } else {
            setBackground(empty);
        }
    }
}