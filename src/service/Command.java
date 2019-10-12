package service;

import model.Board;

public abstract class Command {
	public abstract Board execute(Board board);
}
