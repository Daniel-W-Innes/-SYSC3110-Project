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
    FOX_PLUS_X {
        @Override
        public String toString() {
            return "FoxPX";
        }
    },
    FOX_MINUS_X {
        @Override
        public String toString() {
            return "FoxMX";
        }
    },
    FOX_PLUS_Y {
        @Override
        public String toString() {
            return "FoxPY";
        }
    },
    FOX_MINUS_Y {
        @Override
        public String toString() {
            return "FoxMY";
        }
    },
}
