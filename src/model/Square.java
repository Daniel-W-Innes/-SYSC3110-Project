package model;

public class Square {
    private final boolean isTunnel;
    private final boolean isRaised;
    private final Piece piece;

    Square(boolean isTunnel, boolean isRaised, Piece piece) {
        this.isTunnel = isTunnel;
        this.isRaised = isRaised;
        this.piece = piece;
    }

    public boolean hasPiece() {
        return piece != null;
    }

    public Piece getPiece() {
        return piece;
    }

    boolean isRaised() {
        return isRaised;
    }

    boolean isTunnel() {
        return isTunnel;
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
