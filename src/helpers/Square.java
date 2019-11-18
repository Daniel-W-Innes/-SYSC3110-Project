package helpers;

/**
 * The Square class represents one square in the Board.
 *
 */
public class Square {
    /**
     * If the square is a hole.
     */
    private final boolean isHole;
    /**
     * The hashcode for the move generated with {@code Arrays.hashCode} when the move is initialized.
     */
    private final int hashCode;

    /**
     * Initialize a new piece with the given properties.
     *
     * @param isHole   If the square is a rabbit hole
     */
    public Square(boolean isHole) {
        this.isHole = isHole;
        hashCode = Boolean.hashCode(isHole);
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
     * Two squares are equal when they both contain the same square properties {@code isHole}.
     *
     * @param obj The object to test against
     * @return If obj is the same as this
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (null == obj || obj.getClass() != getClass()) {
            return false;
        }
        Square square = (Square) obj;
        return isHole == square.isHole;
    }

    @Override
    public String toString() {
        return isHole ? "H" : "R";
    }

    /**
     * Get the hashcode for the square.
     *
     * @return The hashcode
     */

    @Override
    public int hashCode() {
        return hashCode;
    }
}