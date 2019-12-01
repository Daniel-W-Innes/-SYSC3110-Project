package view;

import controller.LevelCreator;
import helpers.Point;
import helpers.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

/**
 * Class that holds all of the buttons the provide the necessary functionality
 * to create a new game board.
 */

public class LevelEditorPanel extends JPanel implements ActionListener {

    private LevelEditorButton mushroomButton;
    private LevelEditorButton rabbitButton;
    private LevelEditorButton horizontalFoxButton;
    private LevelEditorButton verticalFoxButton;
    private JButton deleteButton;

    private BoardPanel boardPanel;

    /**
     * Initializes the Level Editor with buttons that the user can press to modify the
     * game board.
     *
     * @param boardPanel reference to the Board that visually holds the board
     */
    public LevelEditorPanel(BoardPanel boardPanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // The points with which the pieces are initialized here do not matter- they can be
        // anywhere on the board.

        mushroomButton = new LevelEditorButton(new Mushroom(new helpers.Point(0, 0)));
        rabbitButton = new LevelEditorButton(new Rabbit(new helpers.Point(0, 0)));
        horizontalFoxButton = new LevelEditorButton(new Fox(Direction.X_AXIS, new helpers.Point(1, 1)));
        verticalFoxButton = new LevelEditorButton(new Fox(Direction.Y_AXIS, new helpers.Point(1, 1)));
        deleteButton = new JButton("Delete");

        deleteButton.setMaximumSize(new Dimension(100, 50));

        mushroomButton.addActionListener(this);
        rabbitButton.addActionListener(this);
        horizontalFoxButton.addActionListener(this);
        verticalFoxButton.addActionListener(this);
        deleteButton.addActionListener(this);

        add(mushroomButton);
        add(rabbitButton);
        add(horizontalFoxButton);
        add(verticalFoxButton);
        add(deleteButton);

        this.boardPanel = boardPanel;

        // By default the game is not in the editing mode
        disableEditingButtons();
    }

    public void disableEditingButtons() {
        mushroomButton.setEnabled(false);
        rabbitButton.setEnabled(false);
        horizontalFoxButton.setEnabled(false);
        verticalFoxButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }

    public void enableEditingButtons() {
        mushroomButton.setEnabled(true);
        rabbitButton.setEnabled(true);
        horizontalFoxButton.setEnabled(true);
        verticalFoxButton.setEnabled(true);
        deleteButton.setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // If a piece button was pressed, then show all of the tiles where the piece can be placed
        // Otherwise, the delete button was pressed, in which case enter the delete mode of the editing mode.
        if (e.getSource() instanceof LevelEditorButton) {
            LevelEditorButton clickedButton = (LevelEditorButton) e.getSource();

            Set<Point> availableBoardSpots = LevelCreator.getAvailableSpots(clickedButton.getPiece());

            // Since the valid locations for pieces are shown through highlighting, any existing
            // highlighting would create confusion. Thus it must be removed.
            boardPanel.unHighlightAllTiles();
            boardPanel.highlightAvailableTiles(clickedButton.getPiece(), availableBoardSpots);
        } else {
            boardPanel.inDeleteMode();
        }
    }
}
