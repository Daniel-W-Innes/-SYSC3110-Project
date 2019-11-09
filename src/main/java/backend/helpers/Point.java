package backend.helpers;

import com.google.common.hash.Funnel;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

public final class Point {
    final int x;
    final int y;
    private final HashCode hashCode;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        hashCode = Hashing.murmur3_128().newHasher().putInt(x).putInt(y).hash();
    }

    public Point(Point point) {
        x = point.x;
        y = point.y;
        hashCode = Hashing.murmur3_128().newHasher().putInt(x).putInt(y).hash();
    }

    Funnel<Point> getFunnel() {
        return (Funnel<Point>) (point, into) -> into.putInt(point.x).putInt(point.y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Point point = (Point) obj;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return hashCode.asInt();
    }

    @Override
    public String toString() {
        return "{" + x + "," + y + "}";
    }
}
