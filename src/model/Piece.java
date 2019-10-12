package model;

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
    FOX {
        @Override
        public String toString() {
            return "Fox";
        }
    }
}
