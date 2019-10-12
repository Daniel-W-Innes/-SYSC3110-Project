package service;

import model.Board;

public abstract class UndoableCommand extends Command {
	public abstract void undo(Board board);
}
