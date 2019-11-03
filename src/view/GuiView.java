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
        newBtn.addActionListener( e -> {
            //game.reset() or game.newGame()
        });
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

    /**
     * Observer pattern
     * @param point
     * @param piece
     */
    @Override
    public void addPiece(Point point, Piece piece) {
        this.boardPanel.addPiece(point, piece);
    }

    /**
     * Observer pattern
     * @param point
     */
    @Override
    public void removePiece(Point point) {
        this.boardPanel.removePiece(point);
    }

    /**
     * Concrete implementation of the View.notifyWin() that handles a win
     */
    @Override
    public void notifyWin() {
        JOptionPane.showMessageDialog(this, "You win!");
    }

    /**
     * The model sends over the initial board terrain layout.
     * @param board the board
     */
    @Override
    public void sendInitialBoard(Board board) {
        this.boardPanel.updateBoardTerrain(board);
        board.getPieces().forEach((point, piece) -> {
            this.boardPanel.addPiece(point, piece);
        });
    }
}