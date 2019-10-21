package helpers;

/**
 * A enum Representing the various types of Pieces.
 */
public enum Piece {
    /**
     * The mushroom game piece.
     */
    MUSHROOM {
        @Override
        public String toString() {
            return "\uD83C\uDF44";
        }
    },
    /**
     * The rabbit game piece.
     */
    RABBIT {
        @Override
        public String toString() {
            return "\uD83D\uDC30";
        }
    },
    /**
     * A part of the fox facing the plus x direction
     */
    FOX_PLUS_X {
        @Override
        public String toString() {
            return "\uD83E\uDD8A";
        }
    },
    /**
     * A part of the fox facing the minus x direction
     */
    FOX_MINUS_X {
        @Override
        public String toString() {
            return "\uD83E\uDD8A";
        }
    },
    /**
     * A part of the fox facing the plus y direction
     */
    FOX_PLUS_Y {
        @Override
        public String toString() {
            return "\uD83E\uDD8A";
        }
    },
    /**
     * A part of the fox facing the minus y direction
     */
    FOX_MINUS_Y {
        @Override
        public String toString() {
            return "\uD83E\uDD8A";
        }
    },
}
