package view;
import java.awt.Point;
import java.util.Scanner;

import controller.Game;
import model.Board;
import service.MoveCommand;

public class TextView implements Viewable {
	private final Game game;
	public TextView (Game game){
		this.game = game;
	}
	
	
	public void update(Board board) {
		System.out.println(board);
		Scanner in = new Scanner(System.in);
     	while(!in.hasNext());
        
     	String input = "";
	    if (in.hasNext()) {
	    	input = in.nextLine();
	    }
	    String[] i = input.split(" ");
	    Point p1 = new Point(Integer.parseInt(i[0]), Integer.parseInt(i[1]));
	    Point p2 = new Point(Integer.parseInt(i[2]), Integer.parseInt(i[3]));
	    game.executeCommand(new MoveCommand(p1, p2));
	}
	
	
	/*
    @Override
    public void setGraphManager(GraphManager graphManager) {
        this.graphManager = graphManager;
    }

    @Override
    public void drawBoard() {
        System.out.println(graphManager.toString());
    }

    @Override
    public void drawMessage(String message) {
        System.out.println(message);
    }*/

}
