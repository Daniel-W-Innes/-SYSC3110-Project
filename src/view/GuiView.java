package view;

import controller.Game;
import helpers.Piece;
import model.Board;

import javax.swing.*;
import java.awt.*;

/**
 * Gui
 */
public class GuiView extends JFrame implements View {
    private final Game game;
    private BoardPanel boardPanel;

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
        JToolBar toolbar = new JToolBar();

        JButton newBtn = new JButton("New Game");
        newBtn.addActionListener(e -> game.resetLevel(this));
        toolbar.add(newBtn);

        JButton loadBtn = new JButton("Load Game");
        loadBtn.addActionListener( e -> {

        });
        toolbar.add(loadBtn);

        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener( e -> {
            //game.save()
        });
        toolbar.add(saveBtn);
        toolbar.add(Box.createHorizontalGlue());


        JButton undoBtn = new JButton("Undo");
        JButton redoBtn = new JButton("Redo");

        toolbar.add(undoBtn);
        toolbar.add(redoBtn);
        this.add(toolbar, BorderLayout.PAGE_START);
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