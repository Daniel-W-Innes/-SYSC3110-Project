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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('{');
        if (isTunnel) {
            stringBuilder.append("Tunnel");
        } else if (isRaised) {
            stringBuilder.append("Raised");
        } else {
            stringBuilder.append("Normal");
        }
        stringBuilder.append(' ');
        if (hasPiece()) {
            stringBuilder.append(piece.toString());
        } else {
            stringBuilder.append("Empty");
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
