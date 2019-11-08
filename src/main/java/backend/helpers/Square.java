package backend.helpers;

import java.util.Arrays;

public class Square {
    private final boolean isHole;
    private final boolean isRaised;
    private final int hashCode;

    public Square(boolean isHole, boolean isRaised) {
        this.isHole = isHole;
        this.isRaised = isRaised;
        hashCode = Arrays.hashCode(new boolean[]{isHole, isRaised});
    }

    public boolean isHole() {
        return isHole;
    }

    private boolean isRaised() {
        return isRaised;
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
        return isHole() == square.isHole() && isRaised() == square.isRaised();
    }

    @Override
    public String toString() {
        if (isHole()) {
            return "H";
        } else if (isRaised()) {
            return "R";
        } else {
            return "_";
        }
    }

    @Override
    public int hashCode() {
        return hashCode;
    }
}
