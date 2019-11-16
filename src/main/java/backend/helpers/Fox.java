package backend.helpers;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import java.util.HashSet;
import java.util.Set;

public final class Fox extends Piece {
    private final Point head;
    private final Point tail;
    private final Direction direction; //The direction the fox is facing e.g. if the head has a bigger x value then direction PLUS_X
    private final HashCode hashCode;

    Fox(Point head, Point tail) {
        this.head = head;
        this.tail = tail;
        this.direction = head.x == tail.x ? head.y > tail.y ? Direction.PLUS_Y : Direction.MINUS_Y : head.x > tail.x ? Direction.PLUS_X : Direction.MINUS_X; //Determine which way the Fox is facing
        hashCode = Hashing.murmur3_128().newHasher() //Because direction is a derived value it does not need to be hashed
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
    private Set<Move> getMoves(Board board, Point start, Point offset) {
        Set<Move> moves = new HashSet<>();
        Point point = new Point(start);//The main loop operator / pointer
        while (true) { //Loop to look along a line in the offset direction
            point = new Point(point.x + offset.x, point.y + offset.y); //Move the point by one offset
            if (!board.hasPiece(point) && point.y <= board.getMax().y && point.x <= board.getMax().x && point.y >= 0 && point.x >= 0) { //Check if the if there is no piece, and if the piece is on the board.
                if (start.equals(head)) {
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

    //This is the function that is initially called by the game in order to determine possible moves from a given end of the fox.
    @Override
    public Set<Move> getMoves(Board board) {
        Set<Move> moves = new HashSet<>();
        switch (direction) {
            case PLUS_Y:
                moves.addAll(getMoves(board, head, new Point(0, 1)));
                moves.addAll(getMoves(board, tail, new Point(0, -1)));
                break;
            case MINUS_Y:
                moves.addAll(getMoves(board, tail, new Point(0, 1)));
                moves.addAll(getMoves(board, head, new Point(0, -1)));
                break;
            case PLUS_X:
                moves.addAll(getMoves(board, head, new Point(1, 0)));
                moves.addAll(getMoves(board, tail, new Point(-1, 0)));
                break;
            case MINUS_X:
                moves.addAll(getMoves(board, tail, new Point(1, 0)));
                moves.addAll(getMoves(board, head, new Point(-1, 0)));
                break;
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
