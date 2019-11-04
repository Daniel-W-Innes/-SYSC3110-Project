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
public class GuiView extends JFrame implements View {
    private final Game game;
    private BoardPanel boardPanel;
    private JToolBar toolbar;

    /**
     * Starts up an instance of the TextView GUI
     * @param game - The Central controller to interact with
     */
    public GuiView(Game game) {
        super("Jumpin game simulator");
        this.game = game;

        this.setSize(900, 960);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.createToolbar();
        this.populateBoard();
        this.setVisible(true);
    }

    /**
     * Creates a toolbar for the gui with the buttons: New game, save game, load game, redo and undo
     */
    private void createToolbar() {
        toolbar = new JToolBar();

        addToolbarButton("Reset Game", e -> game.resetLevel(this));

        addToolbarButton("Load Game", e -> JOptionPane.showMessageDialog(this, "Not Implemented"));
        addToolbarButton("Save Game", e -> JOptionPane.showMessageDialog(this, "Not Implemented"));

        toolbar.add(Box.createHorizontalGlue());
        addToolbarButton("Undo", e -> JOptionPane.showMessageDialog(this, "Not Implemented"));
        addToolbarButton("Redo", e -> JOptionPane.showMessageDialog(this, "Not Implemented"));

        this.add(toolbar, BorderLayout.PAGE_START);
    }


    private void addToolbarButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        toolbar.add(button);
    }

    private void populateBoard() {
        this.boardPanel = new BoardPanel(this.game);
        this.add(this.boardPanel, BorderLayout.CENTER);
    }

    @Override
    public void addPiece(Point point, Piece piece) {
        boardPanel.addPiece(point, piece);
    }

    @Override
    public void removePiece(Point point) {
        this.boardPanel.removePiece(point);
    }

    @Override
    public void notifyWin() {
        JOptionPane.showMessageDialog(this, "You win!");
    }

    @Override
    public void sendInitialBoard(Board board) {
        boardPanel.reset();
        boardPanel.updateBoardTerrain(board);
        board.getPieces().forEach((point, piece) -> boardPanel.addPiece(point, piece));
    }
}