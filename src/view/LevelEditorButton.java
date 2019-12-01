package view;

import helpers.Piece;

import javax.swing.*;
import java.awt.*;

/**
 * Button that can be used to place a piece on to the editable board.
 */

public class LevelEditorButton extends JButton {

    private Piece piece;

    /**
     * Initializes the button with the piece it represents.
     *
     * @param piece the piece which this button should represent
     */
    public LevelEditorButton(Piece piece) {
        setMaximumSize(new Dimension(100, 100));
        setIcon(resizeImageIcon(piece.getLevelEditorIcon()));
        this.piece = piece;
    }

    /**
     * Get the piece which this button represents.
     *
     * @return Piece that this button represents.
     */

    Piece getPiece() {
        return piece;
    }

    /**
     * Since the image icon used by the pieces are not the correct size for the button,
     * they have to be resized to match the dimension of the button.
     *
     * @param imageIcon the icon to resize
     * @return a scaled version of the icon passed in
     */

    private ImageIcon resizeImageIcon(ImageIcon imageIcon) {
        Image scaledImage = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}
