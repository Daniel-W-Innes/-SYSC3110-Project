package backend.helpers;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import java.util.HashSet;
import java.util.Set;

public final class Fox extends Piece {
    private final Point head;
    private final Point tail;
    private final Direction direction;
    private final HashCode hashCode;

    Fox(Point head, Point tail) {
        this.head = head;
        this.tail = tail;
        this.direction = head.x == tail.x ? head.y > tail.y ? Direction.PLUS_Y : Direction.MINUS_Y : head.x > tail.x ? Direction.PLUS_X : Direction.MINUS_X;
        hashCode = Hashing.murmur3_128().newHasher()
                .putObject(head, head.getFunnel())
                .putObject(tail, tail.getFunnel())
                .hash();
    }

    Fox(Point head, Point tail, Direction direction) {
        this.head = head;
        this.tail = tail;
        this.direction = direction;
        hashCode = Hashing.murmur3_128().newHasher()
                .putObject(head, head.getFunnel())
                .putObject(tail, tail.getFunnel())
                .hash();
    }

    //This function determines the possible for the fox in one direction, given one orientation
    private Set<Move> getMoves(Board board, Point start, Point offset, boolean isMovingBackwards) {
        Set<Move> moves = new HashSet<>();
        Point point = new Point(start);//The main loop operator / pointer
        while (true) { //Loop to look along a line in the offset direction
            point = new Point(point.x + offset.x, point.y + offset.y); //Move the point by one offset
            if (!board.hasPiece(point) && point.y <= board.getMax().y && point.x <= board.getMax().x && point.y >= 0 && point.x >= 0) { //Check if the if there is no piece, and if the piece is on the board.
                if ((!isMovingBackwards && start.equals(head)) || isMovingBackwards && start.equals(tail)) { //This determines which case (line 46, 47, 50, 51) the function was called from and therefore if the head is on the trailing side or the leading side of the loop.
                    moves.add(new Move(this, new Fox(point, new Point(point.x - offset.x, point.y - offset.y), direction))); // If the head is on the trailing side of the loop it is initiated to the loop pointer minus the offset.
                } else {
                    moves.add(new Move(this, new Fox(new Point(point.x - offset.x, point.y - offset.y), point, direction))); // If the head is not on the trailing side of the loop it is initiated to the loop pointer minus the offset.
                }
            } else {
                break;
            }
        }
        return moves;
    }

    // Note:
    // There are two likely possibilities for this error.
    //  1.  The following cases are not inclusive and I have missed a case.
    //  2.  The if statement on line 29 is not properly determining which case it is currently processing.

    //This is the function that is initially called by the game in order to determine possible moves from a given end of the fox.
    @Override
    public Set<Move> getMoves(Board board, Point point) {
        Set<Move> moves = new HashSet<>();
        switch (direction) {
            case PLUS_Y:
                moves.addAll(getMoves(board, point, new Point(0, 1), false));
                moves.addAll(getMoves(board, new Point(point.x, point.y - 1), new Point(0, 1), true));
            case MINUS_Y:
                moves.addAll(getMoves(board, point, new Point(0, -1), false));
                moves.addAll(getMoves(board, new Point(point.x, point.y - 1), new Point(0, -1), true));
            case PLUS_X:
                moves.addAll(getMoves(board, point, new Point(1, 0), false));
                moves.addAll(getMoves(board, new Point(point.x - 1, point.y), new Point(1, 0), true));
            case MINUS_X:
                moves.addAll(getMoves(board, point, new Point(1, 0), false));
                moves.addAll(getMoves(board, new Point(point.x - 1, point.y), new Point(1, 0), true));
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
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Fox fox = (Fox) obj;
        return head.equals(fox.head) && tail.equals(fox.tail);
    }

    @Override
    public int hashCode() {
        return hashCode.asInt();
    }
}
