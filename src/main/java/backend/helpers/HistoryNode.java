package backend.helpers;

public class HistoryNode {
    private final HistoryNode previous;
    private final Board board;
    private HistoryNode next;

    public HistoryNode(Board board) {
        this.board = board;
        previous = this;
        next = this;
    }

    private HistoryNode(HistoryNode previous, Board board) {
        this.previous = previous;
        this.board = board;
        this.next = this;
    }

    public Board getBoard() {
        return board;
    }

    public HistoryNode getPrevious() {
        return previous;
    }

    public HistoryNode getNext() {
        return next;
    }

    public void setNext(Board board) {
        this.next = new HistoryNode(this, board);
    }
}
