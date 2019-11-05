package view;

import controller.Game;
import helpers.Piece;
import model.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Gui
 */
public class Gui extends JFrame implements View {
    private static final int WIDTH = 900;
    private static final int HEIGHT = 960;
    private final Game game;
    private BoardPanel boardPanel;
    private JToolBar toolbar;

    /**
     * Starts up an instance of the TextView GUI
     * @param game - The Central controller to interact with
     */
    public Gui(Game game) {
        super("Jumpin game simulator");
        this.game = game;

        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        createToolbar();
        populateBoard();
        setVisible(true);
    }

    /**
     * Creates a toolbar for the gui with the buttons: New game, save game, load game, redo and undo
     */
    private void createToolbar() {
        toolbar = new JToolBar();

        addToolbarButton("Reset Game", e -> game.resetLevel(this));
        addToolbarButton("Load Game", e -> JOptionPane.showMessageDialog(this, "Not Implemented"));
        addToolbarButton("Save Game", e -> JOptionPane.showMessageDialog(this, "Not Implemented"));

        addToolbarButton("Change Level", e -> game.setLevel(this, Integer.parseInt(JOptionPane.showInputDialog(this, "Level Number"))));

        toolbar.add(Box.createHorizontalGlue());
        addToolbarButton("Undo", e -> JOptionPane.showMessageDialog(this, "Not Implemented"));
        addToolbarButton("Redo", e -> JOptionPane.showMessageDialog(this, "Not Implemented"));

        add(toolbar, BorderLayout.PAGE_START);
    }

    /**
     *  Adds a button to the tool bar of this class.
     *
     * @param text the name of the button
     * @param actionListener the ActionListener to be triggered when the button is pressed
     */

    private void addToolbarButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        toolbar.add(button);
    }

    /**
     *  Add the board panel to this frame.
     */

    private void populateBoard() {
        boardPanel = new BoardPanel(game);
        add(boardPanel, BorderLayout.CENTER);
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
     *  Show a pop-up menu telling the user that they won.
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
        boardPanel.reset();
        boardPanel.updateBoardTerrain(board);
        board.getPieces().forEach((point, piece) -> boardPanel.addPiece(point, piece));
    }
}