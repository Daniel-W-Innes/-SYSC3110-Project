package Model;

public enum CommandType {
    EXIT {
        @Override
        public String toString() {
            return "exit";
        }
    }, MOVE {
        @Override
        public String toString() {
            return "move";
        }
    }, RESET {
        @Override
        public String toString() {
            return "reset";
        }
    }
}
