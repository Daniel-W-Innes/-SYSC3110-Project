package backend.helpers;

final class Square {
    private final boolean isHole;

    Square(boolean isHole) {
        this.isHole = isHole;
    }

    boolean isHole() {
        return isHole;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Square square = (Square) obj;
        return isHole() == square.isHole();
    }

    @Override
    public String toString() {
        if (isHole()) {
            return "H";
        } else {
            return "R";
        }
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(isHole);
    }
}
