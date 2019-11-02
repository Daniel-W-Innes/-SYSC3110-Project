package view;

import controller.Game;
import helpers.Piece;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GameGUI extends JFrame implements View {
    view.Board board;

    public GameGUI(Game game) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        super("Jumpin game simulator");

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        setSize(900, 960);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setUndecorated(true);
        createToolBar();
        createBoardView(game);
        setVisible(true);
    }

    private void createBoardView(Game game){
        this.board = new view.Board(game);
        this.add(board, BorderLayout.CENTER);
    }

    private void createToolBar(){
        JToolBar toolBar = new JToolBar();

        JButton newBtn = new JButton("New Game");
        JButton loadBtn = new JButton("Load Game");
        JButton saveBtn = new JButton("Save");

        toolBar.add(newBtn);
        toolBar.add(loadBtn);
        toolBar.add(saveBtn);

        this.add(toolBar, BorderLayout.PAGE_START);
    }

    /**
     * Method called by the model. Tells the View the board state through deltas.
     * @param point
     * @param piece
     */
    @Override
    public void addPiece(Point point, Piece piece) {
        this.board.addPiece(point, piece);
    }

    /**
     * Method called by the model. Tells the View the board state through deltas.
     * @param point
     */
    @Override
    public void removePiece(Point point) {
        this.board.removePiece(point);
    }
}
