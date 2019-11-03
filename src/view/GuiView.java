package view;

import controller.Game;
import helpers.Piece;
import model.Board;
import model.Model;

import javax.swing.*;
import java.awt.*;

public class GuiView extends JFrame implements view.View {
    private final Game game;
    private BoardPanel boardPanel;
    private boolean keepSuffering;

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
    private void createToolbar() {
        JToolBar toolbar = new JToolBar();

        JButton newBtn = new JButton("New Game");
        JButton loadBtn = new JButton("Load Game");
        JButton saveBtn = new JButton("Save");

        toolbar.add(newBtn);
        toolbar.add(loadBtn);
        toolbar.add(saveBtn);

        this.add(toolbar, BorderLayout.PAGE_START);
    }

    private void populateBoard() {
        this.boardPanel = new BoardPanel(this.game);
        this.add(this.boardPanel, BorderLayout.CENTER);
    }

    /**
     * Observer pattern
     * @param point
     * @param piece
     */
    @Override
    public void addPiece(Point point, Piece piece) {

    }

    /**
     * Observer pattern
     * @param point
     */
    @Override
    public void removePiece(Point point) {

    }

    /**
     * Concrete implementation of the View.notifyWin() that handles a win
     */
    @Override
    public void notifyWin() {
        JOptionPane.showMessageDialog(this, "You win!");
    }

    @Override
    public void sendBoardTerrain(Board board) {
        //TODO: when I get the initial board terrain, put in the appropriate icons
        this.boardPanel.updateBoardTerrain(board);
    }
}
