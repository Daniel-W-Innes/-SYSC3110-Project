package frontend;

import backend.helpers.Board;
import backend.helpers.Game;
import backend.helpers.Move;
import backend.models.Model;

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
        setVisible(true);
    }

    /**
     * Creates a toolbar for the gui with the buttons: New game, save game, load game, redo and undo
     */
    private void createToolbar() {
        toolbar = new JToolBar();

        //addToolbarButton("Reset Game", e -> game.resetLevel(this));
        addToolbarButton("Load Game", e -> JOptionPane.showMessageDialog(this, "Not Implemented"));
        addToolbarButton("Save Game", e -> JOptionPane.showMessageDialog(this, "Not Implemented"));

        //addToolbarButton("Change Level", e -> game.setLevel(this, Integer.parseInt(JOptionPane.showInputDialog(this, "Level Number"))));

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

    @Override
    public void update(Model m){
        boardPanel.update((Board) m);
    }

    @Override
    public void update(Move m) {
        boardPanel.update(m);
    }
}