package model;

/**
 * A enum Representing the various types of Pieces
 *
 * FOX_PLUS_X -> A part of the fox facing the positive x direction
 * FOX_MINUS_X -> A part of the fox facing the negative x direction
 * etc.
 */
public enum Piece {
    MUSHROOM {
        @Override
        public String toString() {
            return "Mushroom";
        }
    },
    RABBIT {
        @Override
        public String toString() {
            return "Rabbit";
        }
    },
    FOX_PLUS_X {
        @Override
        public String toString() {
            return "Fox";
        }
    },
    FOX_MINUS_X {
        @Override
        public String toString() {
            return "Fox";
        }
    },
    FOX_PLUS_Y {
        @Override
        public String toString() {
            return "Fox";
        }
    },
    FOX_MINUS_Y {
        @Override
        public String toString() {
            return "Fox";
        }
    },
}
