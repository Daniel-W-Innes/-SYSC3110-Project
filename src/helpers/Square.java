package helpers;

/**
 * The Square class represents one square in the Board.
 * @author frank liu
 */
public class Square {
    /**
     * If the square is a hole.
     */
    private final boolean isHole;
    /**
     * If the square is a raised.
     */
    private final boolean isRaised;
    /**
     * The piece located on the square, nullable.
     */
    private Piece piece;

    /**
     * The hashcode for the move generated with stringBuilders when the move is initialized.
     * The hashcode is regenerated when {@code setPiece} or {@code removePiece} is called.
     */
    private int hashCode;

    /**
     * Initialize a new piece with the given properties.
     *
     * @param isHole If the square is a rabbit hole
     * @param isRaised If the square is raised
     * @param piece The Piece that is located on it, nullable
     */
    public Square(boolean isHole, boolean isRaised, Piece piece) {
        this.isHole = isHole;
        this.isRaised = isRaised;
        this.piece = piece;
        hashCode = genHashCode();
    }

    /**
     * Generated a hashcode for the square.
     *
     * @return The new hashcode
     */
    private int genHashCode() {
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
        return stringBuilder.toString().hashCode();
    }

    /**
     * Get if the square contains a piece.
     *
     * @return If square contains a piece
     */
    public boolean hasPiece() {
        return piece != null;
    }

    /**
     * Get the Piece that is on the square.
     *
     * @return The piece that is on the square or null if there is no piece
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Change the piece that is on the square and regenerated the hashCode.
     *
     * @param piece The new piece
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
        hashCode = genHashCode();
    }

    /**
     * Removes the piece that is on this square and regenerated the hashCode.
     */
    void removePiece() {
        this.piece = null;
        hashCode = genHashCode();
    }

    /**
     * Get if the square is a raised.
     * Note: All holes are raised.
     *
     * @return If the square is a raised
     */
    public boolean isRaised() {
        return isRaised;
    }

    /**
     * Get if the square is a hole.
     * Note: All holes are raised.
     *
     * @return If the square is a hole
     */
    public boolean isHole() {
        return isHole;
    }

    /**
     * Two squares are equal when they both contain the same square properties {@code isHole} and {@code isRaised} and contains the same piece.
     *
     * @param obj The object to test against
     * @return If obj is the same as this
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

    /**
     * Get the hashcode for the square. This hashcode is generated with stringBuilders when the move is initialized. The hashcode is regenerated when {@code setPiece} or {@code removePiece} is called.
     *
     * @return The hashcode
     */

    @Override
    public int hashCode() {
        return hashCode;
    }

    /**
     * Get a string representation of the square. The String is formatted as follows {square type \s Piece that is on the square} e.g. {Hole Rabbit}.
     *
     * @return The representative string
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (isHole) {
            stringBuilder.append("H");
        } else if (isRaised) {
            stringBuilder.append("R");
        } else {
            stringBuilder.append("_");
        }
        if (hasPiece()) {
            stringBuilder.append(piece.toString());
        } else {
            stringBuilder.append("_");
        }
        return stringBuilder.toString();
    }
}