package Model;

public class Square {
    private final boolean isTunnel;
    private final boolean isRaised;
    private Piece piece;

    public Square(boolean isTunnel, boolean isRaised, Piece piece) {
        this.isTunnel = isTunnel;
        this.isRaised = isRaised;
        this.piece = piece;
    }

    public boolean isTunnel() {
        return isTunnel;
    }

    public Piece getPiece() {
        return piece;
    }

    public boolean hasPiece() {
        return piece != null;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public boolean isRaised() {
        return isRaised;
    }
}
