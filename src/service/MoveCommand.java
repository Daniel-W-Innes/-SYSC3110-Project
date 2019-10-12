package service;

import java.awt.Point;

import model.Board;

public class MoveCommand extends Command{
	public final Point p1;
	public final Point p2;
	
	public MoveCommand(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
	}

	@Override
	public Board execute(Board board) {
		return board.move(p1, p2);
	}
	
	
}
