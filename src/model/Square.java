package model;

public class Square {
    private final boolean isHole;
    private final boolean isRaised;
    private Piece piece;
    private final int hashCode;

    Square(boolean isHole, boolean isRaised, Piece piece) {
        this.isHole = isHole;
        this.isRaised = isRaised;
        this.piece = piece;
        StringBuilder stringBuilder = new StringBuilder();
        if (isHole) {
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

    public void setPiece(Piece p) {
        this.piece = p;
    }

    public Piece getPiece() {
        return piece;
    }

    boolean isRaised() {
        return isRaised;
    }

    boolean isHole() {
        return isHole;
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
        return hasPiece() != square.hasPiece() || isHole() != square.isHole() || isRaised() != square.isRaised() || !hasPiece() || getPiece().equals(square.getPiece());
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('{');
        if (isHole) {
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
