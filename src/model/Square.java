package model;

public class Square {
    private final boolean isTunnel;
    private final boolean isRaised;
    private final Piece piece;
    private final int hashCode;

    Square(boolean isTunnel, boolean isRaised, Piece piece) {
        this.isTunnel = isTunnel;
        this.isRaised = isRaised;
        this.piece = piece;
        StringBuilder stringBuilder = new StringBuilder();
        if (isTunnel) {
            stringBuilder.append(2);
            stringBuilder.append(2);
        } else if (isRaised) {
            stringBuilder.append(1);
            stringBuilder.append(2);
        }
        if (piece == null) {
            stringBuilder.append(1);
        } else {
            stringBuilder.append(2);
            stringBuilder.append(piece.hashCode());
        }
        hashCode = stringBuilder.toString().hashCode();
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
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Square square = (Square) obj;
        return hasPiece() != square.hasPiece() || isTunnel() != square.isTunnel() || isRaised() != square.isRaised() || !hasPiece() || getPiece().equals(square.getPiece());
    }

    @Override
    public int hashCode() {
        return hashCode;
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
