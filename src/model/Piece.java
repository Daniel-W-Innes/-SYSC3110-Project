package model;

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
            return "Mushroom";
        }
    },
    /**
     * The rabbit game piece.
     */
    RABBIT {
        @Override
        public String toString() {
            return "Rabbit";
        }
    },
    /**
     * A part of the fox facing the plus x direction
     */
    FOX_PLUS_X {
        @Override
        public String toString() {
            return "Fox";
        }
    },
    /**
     * A part of the fox facing the minus x direction
     */
    FOX_MINUS_X {
        @Override
        public String toString() {
            return "Fox";
        }
    },
    /**
     * A part of the fox facing the plus y direction
     */
    FOX_PLUS_Y {
        @Override
        public String toString() {
            return "Fox";
        }
    },
    /**
     * A part of the fox facing the minus y direction
     */
    FOX_MINUS_Y {
        @Override
        public String toString() {
            return "Fox";
        }
    },
}
