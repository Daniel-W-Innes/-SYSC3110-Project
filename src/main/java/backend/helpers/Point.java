package backend.helpers;

import java.util.Arrays;

public final class Point {
    final int x;
    final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point point) {
        x = point.x;
        y = point.y;
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
        return Arrays.hashCode(new int[]{x, y});
    }

    @Override
    public String toString() {
        return "{" + x + "," + y + "}";
    }
}
