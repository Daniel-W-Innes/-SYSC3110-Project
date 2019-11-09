package backend.helpers;

import backend.models.ImmutableBoard;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Fox implements Piece {
    private final Point head;
    private final Point tail;

    Fox(Point head, Point tail) {
        this.head = head;
        this.tail = tail;
    }

    private Set<Move> getMoves(ImmutableBoard board, Point start, Point offset, boolean isMovingBackwards) {
        Set<Move> moves = new HashSet<>();
        Point point = new Point(start);
        while (true) {
            point = new Point(point.x + offset.x, point.y + offset.y);
            if (!board.hasPiece(point) && point.y <= board.getMax().y && point.x <= board.getMax().x && point.y >= 0 && point.x >= 0) {
                if ((!isMovingBackwards && start.equals(head)) || isMovingBackwards && start.equals(tail)) {
                    moves.add(new Move(this, new Fox(point, new Point(point.x - offset.x, point.y - offset.y))));
                } else {
                    moves.add(new Move(this, new Fox(new Point(point.x - offset.x, point.y - offset.y), point)));
                }
            } else {
                break;
            }
        }
        return moves;
    }


    @Override
    public Set<Move> getMoves(ImmutableBoard board, Point point) {
        Set<Move> moves = new HashSet<>();
        if (head.x == tail.x) {
            moves.addAll(getMoves(board, point, new Point(0, 1), false));
            moves.addAll(getMoves(board, new Point(point.x, point.y - 1), new Point(0, -1), true));
        } else {
            moves.addAll(getMoves(board, point, new Point(1, 0), false));
            moves.addAll(getMoves(board, new Point(point.x - 1, point.y), new Point(-1, 0), true));
        }
        return moves;
    }

    @Override
    public boolean occupies(Point point) {
        return head.equals(point) || tail.equals(point);
    }

    @Override
    public Set<Point> occupies() {
        return Set.of(head, tail);
    }

    @Override
    public String toString() {
        return "F";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Fox) {
            return super.equals(obj) && head.equals(((Fox) obj).head) && tail.equals(((Fox) obj).tail);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new int[]{super.hashCode(), head.hashCode(), tail.hashCode()});
    }
}
