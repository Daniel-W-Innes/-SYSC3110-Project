package view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Frame extends JFrame {
    view.Board board;

    public static void main(String[] args){
        new Frame();
    }

    public Frame(){
        super("Rabbit and Foxes");
        setSize(500, 500);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setUndecorated(true);
        createToolBar();
        createBoardView();
        setVisible(true);
    }

    private void createBoardView(){
        board = new view.Board();
        add(board, BorderLayout.CENTER);
    }

    private void createToolBar(){
        JToolBar toolBar = new JToolBar();

        JButton newBtn = new JButton("New Game");
        JButton loadBtn = new JButton("Load Game");
        JButton saveBtn = new JButton("Save");

        toolBar.add(newBtn);
        toolBar.add(loadBtn);
        toolBar.add(saveBtn);

        add(toolBar, BorderLayout.PAGE_START);
    }

}
