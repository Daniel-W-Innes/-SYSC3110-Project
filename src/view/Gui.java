package view;

import controller.Game;
import controller.LevelCreator;
import helpers.Move;
import helpers.Piece;
import helpers.Point;
import model.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Optional;

/**
 * GUI for the game.
 */
public class Gui extends JFrame implements View {
    private static final int WIDTH = 1025;
    private static final int HEIGHT = 960;
    private final BoardPanel boardPanel;
    private LevelEditorPanel levelEditorPanel;

    /**
     * Starts up an instance of the TextView GUI
     *
     * @param game - The Central controller to interact with
     */
    public Gui(Game game) {
        super("Jumpin game simulator");

        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        createToolbar(game);
        boardPanel = new BoardPanel(game);
        levelEditorPanel = new LevelEditorPanel(boardPanel);
        add(boardPanel, BorderLayout.CENTER);
        add(levelEditorPanel, BorderLayout.EAST);
        setVisible(true);
    }

    /**
     * Creates a toolbar for the gui with the buttons: New game, save game, load game, redo and undo
     */
    private void createToolbar(Game game) {
        JToolBar toolbar = new JToolBar();

        addToolbarButton(toolbar, "Reset Game", e -> {
            try {
                game.resetLevel(this);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Failed to load");
            }
        });

        addToolbarButton(toolbar, "Load Game", e -> {
            try {
                String fileName = JOptionPane.showInputDialog(this, "File Name");
                if (null != fileName) {
                    game.load(this, fileName);
                    boardPanel.toggleInLevelEditor(false); // Re-enable game logic
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Failed to load");
            }
        });

        addToolbarButton(toolbar, "Save Game", e -> {
            try {
                String fileName = JOptionPane.showInputDialog(this, "File Name");
                if (null != fileName) {
                    game.save(fileName);
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Failed to save");
            }
        });

        addToolbarButton(toolbar, "Change Level", e ->
        {
            try {
                String levelName = JOptionPane.showInputDialog(this, "Level Name");
                if (null != levelName) {
                    game.setLevel(this, levelName);
                }
            } catch (IOException exception) {
                JOptionPane.showMessageDialog(this, "You must enter a valid level.");
            }
        });

        toolbar.add(Box.createHorizontalGlue());
        addToolbarButton(toolbar, "Hint", e -> {
            if (game.gameWon()) {
                JOptionPane.showMessageDialog(this, "The game is already won.");
            } else {
                // While a solution is being generated, this Thread is put to sleep. Thus the InterruptedException
                if (boardPanel.inEditingMode()) {
                    try {
                        if (!LevelCreator.solutionExists()) {
                            JOptionPane.showMessageDialog(this, "No solution found for the board");
                        } else {
                            JOptionPane.showMessageDialog(this, "There is solution for the board");
                        }
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    Optional<Move> hint = null;

                    hint = game.hint();

                    if (hint.isPresent()) {
                        boardPanel.showHint(hint.get());
                    } else {
                        JOptionPane.showMessageDialog(this, "Hint is still loading try again later");
                    }
                }
            }
        });
        addToolbarButton(toolbar, "Undo", e -> game.undo());
        addToolbarButton(toolbar, "Redo", e -> game.redo());
        addToolbarButton(toolbar, "Enter Game Design Mode", e ->
        {
            LevelCreator.showGameBeingCreated(this);
            boardPanel.unHighlightAllTiles();
            boardPanel.toggleInLevelEditor(true);
            levelEditorPanel.enableEditingButtons();

        });

        addToolbarButton(toolbar, "Exit Game Design Mode", e ->
        {
            if (boardPanel.inEditingMode()) {
                try {
                    String fileName = JOptionPane.showInputDialog(this, "File Name");
                    if (null != fileName) {
                        LevelCreator.saveLevel(fileName);
                        boardPanel.toggleInLevelEditor(false);
                        game.load(this, fileName);
                    }
                } catch (IOException | InterruptedException ex) {
                    JOptionPane.showMessageDialog(this, "Failed to save");
                }

                levelEditorPanel.disableEditingButtons();
            }

        });

        add(toolbar, BorderLayout.PAGE_START);
    }

    /**
     * Adds a button to the tool bar of this class.
     *
     * @param text           the name of the button
     * @param actionListener the ActionListener to be triggered when the button is pressed
     */

    private void addToolbarButton(JToolBar toolbar, String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        toolbar.add(button);
    }

    /**
     * Visually add a piece to the rendered game shown.
     *
     * @param point the point where the piece should be rendered to
     * @param piece the piece that should be painted at the given location
     */

    @Override
    public void addPiece(Point point, Piece piece) {
        boardPanel.addPiece(point, piece);
    }

    /**
     * Visually remove a piece from the rendered game shown.
     *
     * @param point the point where the piece should not longer be rendered to
     */

    @Override
    public void removePiece(Point point) {
        boardPanel.removePiece(point);
    }

    /**
     * Show a pop-up menu telling the user that they won.
     */

    @Override
    public void notifyWin() {
        JOptionPane.showMessageDialog(this, "You win!");
    }

    /**
     * Visually create the initial board state at the beginning of the game.
     *
     * @param board the board that is to be shown visually
     */

    @Override
    public void sendInitialBoard(Board board) {
        boardPanel.updateBoardTerrain(board);
        board.getPieces().forEach(boardPanel::addPiece);
    }
}