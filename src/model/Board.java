package model;

import java.awt.*;
import java.util.*;

public class Board implements Iterable<Map.Entry<ImmutablePoint, Square>> {

    public static class Copier {
        private final Map<ImmutablePoint, Square> board;
        private final Point max;
        private boolean isVictory;
        private boolean isVictoryValid;
        private boolean isMaxValid;

        public Copier(Board board) {
            this.board = new HashMap<>(board.getBoard());
            max = board.getMax().getMutablePoint();
            isVictory = board.isVictory();
            isMaxValid = true;
            isVictoryValid = true;
        }

        public Copier removePieces(ImmutablePoint loc) {
            Square square = board.get(loc);
            if (square.isTunnel()) {
                board.put(loc, new Square(true, true, null));
            } else if (square.isRaised()) {
                board.put(loc, new Square(false, true, null));
            } else {
                board.remove(loc);
                if (max.getX() == loc.getX() || max.getY() == loc.getY()) {
                    isMaxValid = false;
                }
            }
            if (!square.isTunnel() && !isVictory && square.getPiece() == Piece.RABBIT) {
                isVictoryValid = false;
            }
            return this;

        }

        public Copier addPieces(ImmutablePoint loc, Piece piece) {
            if (board.containsKey(loc)) {
                Square square = board.get(loc);
                if (!square.hasPiece()) {
                    board.put(loc, new Square(square.isTunnel(), square.isRaised(), piece));
                    if (!square.isTunnel() && piece == Piece.RABBIT) {
                        isVictory = false;
                    }
                }
            } else {
                board.put(loc, new Square(false, false, piece));
                updateMax(max, loc);
            }
            return this;
        }

        private void updateMax(Point max, ImmutablePoint point) {
            if (point.getX() > max.x) {
                max.x = point.getX();
            }
            if (point.getY() > max.y) {
                max.y = point.getY();
            }
        }

        public Board build() {
            if (!isMaxValid) {
                for (ImmutablePoint point : board.keySet()) {
                    updateMax(max, point);
                }
            }
            if (!isVictoryValid) {
                isVictory = true;
                for (Square square : board.values()) {
                    if (square.getPiece() == Piece.RABBIT && !square.isTunnel()) {
                        isVictory = false;
                        break;
                    }
                }
            }
            return new Board(board, new ImmutablePoint(max), isVictory);
        }
    }

    public static class Builder {
        private final Set<ImmutablePoint> tunnels;
        private final Set<ImmutablePoint> raisedSquares;
        private final Map<ImmutablePoint, Piece> pieces;

        public Builder() {
            tunnels = new HashSet<>();
            pieces = new HashMap<>();
            raisedSquares = new HashSet<>();
        }

        public Builder addTunnel(ImmutablePoint loc) {
            tunnels.add(loc);
            return this;
        }

        public Builder addRaisedSquare(ImmutablePoint loc) {
            raisedSquares.add(loc);
            return this;
        }

        public Builder addPieces(ImmutablePoint loc, Piece piece) {
            pieces.put(loc, piece);
            return this;
        }

        private void updateMax(Point max, ImmutablePoint point) {
            if (point.getX() > max.x) {
                max.x = point.getX();
            }
            if (point.getY() > max.y) {
                max.y = point.getY();
            }
        }

        public Board build() {
            Map<ImmutablePoint, Square> board = new HashMap<>();
            Point max = new Point(0, 0);
            boolean isVictory = true;
            for (Map.Entry<ImmutablePoint, Piece> entry : pieces.entrySet()) {
                if (entry.getValue() == Piece.RABBIT && !tunnels.contains(entry.getKey())) {
                    isVictory = false;
                    break;
                }
            }
            for (ImmutablePoint point : tunnels) {
                board.put(point, new Square(true, true, pieces.get(point)));
                pieces.remove(point);
                updateMax(max, point);
            }
            for (ImmutablePoint point : raisedSquares) {
                board.put(point, new Square(false, true, pieces.get(point)));
                pieces.remove(point);
                updateMax(max, point);
            }
            for (Map.Entry<ImmutablePoint, Piece> entry : pieces.entrySet()) {
                board.put(entry.getKey(), new Square(false, false, entry.getValue()));
                updateMax(max, entry.getKey());
            }
            return new Board(board, new ImmutablePoint(max), isVictory);
        }
    }

    private final Map<ImmutablePoint, Square> board;
    private final ImmutablePoint max;
    private final boolean isVictory;

    private Board(Map<ImmutablePoint, Square> board, ImmutablePoint max, boolean isVictory) {
        this.board = Collections.unmodifiableMap(board);
        this.max = max;
        this.isVictory = isVictory;
    }

    public Square getSquare(ImmutablePoint loc) {
        return board.get(loc);
    }

    public boolean hasSquare(ImmutablePoint loc) {
        return board.containsKey(loc);
    }

    private boolean isVictory() {
        return isVictory;
    }

    private Map<ImmutablePoint, Square> getBoard() {
        return board;
    }

    public ImmutablePoint getMax() {
        return max;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ImmutablePoint point;
        for (int x = 0; x <= max.getX(); x++) {
            for (int y = 0; y <= max.getY(); y++) {
                point = new ImmutablePoint(x, y);
                stringBuilder.append('|');
                stringBuilder.append(String.format("%1$" + 17 + "s", hasSquare(point) ? getSquare(point).toString() : "Empty"));
            }
            stringBuilder.append('|');
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Board board = (Board) obj;
        return getBoard().equals(board.getBoard());
    }

    @Override
    public int hashCode() {
        return getBoard().hashCode();
    }

    @Override
    public Iterator<Map.Entry<ImmutablePoint, Square>> iterator() {
        return board.entrySet().iterator();
    }
}
