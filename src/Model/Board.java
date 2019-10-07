package Model;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board {

    public static class Builder {
        Point size;
        Set<Point> tunnels;
        Set<Point> raisedSquares;
        Map<Point, Piece> pieces;

        public Builder(Point size) {
            this.size = size;
            tunnels = new HashSet<>();
            pieces = new HashMap<>();
        }

        public Builder addTunnel(Point Loc) {
            tunnels.add(Loc);
            return this;
        }

        public Builder addRaisedSquare(Point Loc) {
            raisedSquares.add(Loc);
            return this;
        }

        public Builder addPieces(Point Loc, Piece piece) {
            pieces.put(Loc, piece);
            return this;
        }

        public Board build() {
            Square[][] board = new Square[size.x][size.y];
            for (int x = 0; x < size.x; x++) {
                for (int y = 0; y < size.y; y++) {
                    board[x][y] = null;
                }
            }
            for (Point point : tunnels) {
                board[point.x][point.y] = new Square(true, true, pieces.get(point));
                pieces.remove(point);
            }
            for (Point point : raisedSquares) {
                board[point.x][point.y] = new Square(false, true, pieces.get(point));
                pieces.remove(point);
            }
            for (Map.Entry<Point, Piece> entry : pieces.entrySet()) {
                board[entry.getKey().x][entry.getKey().y] = new Square(false, false, entry.getValue());
            }
            return new Board(board);
        }
    }

    private Square[][] board;

    private Board(Square[][] board) {
        this.board = board;
    }

    public Square getSquare(Point loc) {
        assert isValidLoc(loc);
        return (board[loc.x][loc.y]);
    }

    public void movePiece(Point oldLoc, Point newLoc) {
        assert isValidLoc(oldLoc);
        assert isValidLoc(newLoc);
        assert board[oldLoc.x][oldLoc.y].hasPiece();
        assert !board[newLoc.x][newLoc.y].hasPiece();
        board[newLoc.x][newLoc.y].setPiece(board[oldLoc.x][oldLoc.y].getPiece());
    }

    private boolean isValidLoc(Point loc) {
        return loc.x < board.length && loc.y < board[0].length;
    }
}
