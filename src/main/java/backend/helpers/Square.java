package backend.helpers;

import com.google.common.hash.Funnel;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

final class Square {
    private final boolean isHole;
    private final HashCode hashCode;

    Square(boolean isHole) {
        this.isHole = isHole;
        hashCode = Hashing.murmur3_128().newHasher().putBoolean(isHole).hash();
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
        return isHole == square.isHole;
    }

    @Override
    public String toString() {
        return isHole ? "H" : "R";
    }

    public Funnel<Square> getFunnel() {
        return (from, into) -> into.putBoolean(from.isHole);
    }

    @Override
    public int hashCode() {
        return hashCode.asInt();
    }
}
