package helpers;

import java.util.Arrays;

/**
 * The Square class represents one square in the Board.
 *
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
     * The hashcode for the move generated with {@code Arrays.hashCode} when the move is initialized.
     */
    private final int hashCode;

    /**
     * Initialize a new piece with the given properties.
     *
     * @param isHole   If the square is a rabbit hole
     * @param isRaised If the square is raised
     */
    public Square(boolean isHole, boolean isRaised) {
        this.isHole = isHole;
        this.isRaised = isRaised;
        hashCode = Arrays.hashCode((new boolean[]{isHole, isRaised}));
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
        if (null == obj || obj.getClass() != getClass()) {
            return false;
        }
        Square square = (Square) obj;
        return isHole == square.isHole && isRaised == square.isRaised;
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
}