package helpers;

import protos.PointOuterClass;

import java.util.Objects;

public final class Point {
    public final int x;
    public final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point point) {
        x = point.x;
        y = point.y;
    }

    public Point() {
        x = 0;
        y = 0;
    }

    public Point(PointOuterClass.Point point) {
        x = point.getX();
        y = point.getY();
    }

    public Point move(Point offset) {
        return new Point(x + offset.x, y + offset.y);
    }


    Point moveX(int offsetX) {
        return new Point(x + offsetX, y);
    }

    Point moveY(int offsetY) {
        return new Point(x, y + offsetY);
    }

    public PointOuterClass.Point toProto() {
        return PointOuterClass.Point.newBuilder().setX(x).setY(y).build();
    }

    @Override
    public String toString() {
        return "{" + x + ", " + y + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
