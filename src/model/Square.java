package model;

/**
 * The Square class represents one square in the Board.
 * Each square will store the properties isHole?, isRaised?, and the current piece that is located on it
 */
public class Square {
    private final boolean isHole;
    private final boolean isRaised;
    private Piece piece;
    private final int hashCode;

    /**
     * Creates a piece instance with the given properties
     * @param isHole - true if the Square contains a Rabbit hole
     * @param isRaised - true if the Square is raised
     * @param piece - the Piece that is Currently located on it (Either an instance of Piece enum or null)
     */
    public Square(boolean isHole, boolean isRaised, Piece piece) {
        this.isHole = isHole;
        this.isRaised = isRaised;
        this.piece = piece;

        //create Hashcode as Square is used intensively in HashMaps
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

    /**
     * Returns true whether the current square contains a piece
     * @return true - if current square contains a piece
     *         false - otherwise
     */
    public boolean hasPiece() {
        return piece != null;
    }

    /**
     * Returns the Piece that is currently on this square
     * @return The piece that is currently on this Square or
     *         null if there is no piece
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Change the piece that is on this Square
     * @param piece - The piece that is on this square or null if the square is to have no pieces
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * Removes the piece that is on this square
     */
    public void removePiece() {
        this.piece = null;
    }

    /**
     * Returns if the current square is raised
     * @return true - if the current square is raised
     *         false - otherwise
     */
    boolean isRaised() {
        return isRaised;
    }

    /**
     * Returns if the current square contains a hole
     * @return true - if the current square contains a hole
     *         false - otherwise
     */
    boolean isHole() {
        return isHole;
    }

    /**
     * Two squares are equal when they both contain the same square properties (isHole?, isRaised?) and contains the same piece
     */
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

    /**
     * Returns the String of format "{Square type} {Piece that is on the square}"
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('{');
        if (isHole) {
            stringBuilder.append("Hole");
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