package model;

import helpers.*;
import view.View;

import java.awt.*;
import java.util.List;
import java.util.*;

public class Board implements Model {
    private final Map<Point, Square> board;
    private final Point max;
    private final Map<Point, Piece> pieces;
    private List<View> views;

    private Board(Map<Point, Square> board, Map<Point, Piece> pieces, Point max) {
        this.board = Collections.unmodifiableMap(board);
        this.pieces = new HashMap<>(pieces);
        this.max = max;
        views = new ArrayList<>();
    }

    public Board(Board board) {
        this.board = board.getBoard();
        pieces = new HashMap<>(board.getPieces());
        max = board.getMax();
        views = new ArrayList<>(board.getViews());
    }

    private List<View> getViews() {
        return views;
    }

    private Map<Point, Square> getBoard() {
        return board;
    }

    public void addPiece(Point point, Piece piece) {
        getPieces().put(point, piece);
    }

    public Map<Point, Piece> getPieces() {
        return pieces;
    }

    private void movePiece(Move move) {
        getPieces().put(move.getEnd(), getPieces().get(move.getStart()));
        for (View view : views) {
            view.addPiece(move.getEnd(), getPieces().get(move.getStart()));
        }
        getPieces().remove(move.getStart());
        for (View view : views) {
            view.removePiece(move.getStart());
        }
    }

    public boolean hasPiece(Point point) {
        return getPieces().containsKey(point);
    }

    public Point getMax() {
        return max;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Point point;
        for (int x = 0; x <= getMax().x; x++) {
            for (int y = 0; y <= getMax().y; y++) {
                point = new Point(x, y);
                stringBuilder.append('|');
                stringBuilder.append(hasSquare(point) ? getSquare(point).toString() : "_");
                stringBuilder.append(hasPiece(point) ? getPiece(point).toString() : "_");
            }
            stringBuilder.append('|');
//            stringBuilder.append("\n");
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
        return getBoard().equals(board.getBoard()) && getPieces().equals(board.getPieces());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new int[]{getBoard().hashCode(), getPieces().hashCode()});
    }

    public Piece getPiece(Point point) {
        return getPieces().get(point);
    }

    private boolean hasSquare(Point point) {
        return getBoard().containsKey(point);
    }

    private Square getSquare(Point point) {
        return getBoard().get(point);
    }

    public void movePieces(List<Move> moves) {
        for (Move move : moves) {
            movePiece(move);
        }
    }

    public boolean isVictory() {
        return getPieces().entrySet().stream()
                .filter(entry -> entry.getValue() instanceof Rabbit)
                .allMatch(entry -> hasSquare(entry.getKey()) && getSquare(entry.getKey()).isHole());
    }

    @Override
    public void addView(View view) {
        views.add(view);
    }

    @Override
    public void removeView(View view) {
        views.remove(view);
    }

    public static class Builder {
        private final Set<Point> holes;
        private final Set<Point> raisedSquares;
        private final Map<Point, Piece> pieces;


        public Builder(boolean useDefaultMap) {
            if (useDefaultMap) {
                holes = Set.of(new Point(0, 0), new Point(4, 4), new Point(0, 4), new Point(4, 0), new Point(2, 2));
                raisedSquares = Set.of(new Point(0, 2), new Point(2, 0), new Point(2, 4), new Point(4, 2));
            } else {
                holes = new HashSet<>();
                raisedSquares = new HashSet<>();
            }
            pieces = new HashMap<>();
        }

        public Builder addHole(Point point) {
            holes.add(point);
            return this;
        }

        public Builder addRaisedSquares(Point point) {
            raisedSquares.add(point);
            return this;
        }

        public Builder addPieces(Point loc, Piece piece) {
            pieces.put(loc, piece);
            return this;
        }

        private void updateMax(Point max, Point point) {
            if (point.x > max.x) {
                max.x = point.x;
            }
            if (point.y > max.y) {
                max.y = point.y;
            }
        }

        public Board build() {
            Point max = new Point(0, 0);
            Map<Point, Square> board = new HashMap<>();
            for (Point point : raisedSquares) {
                board.put(point, new Square(false, true));
                updateMax(max, point);
            }
            for (Point point : holes) {
                board.put(point, new Square(true, true));
                updateMax(max, point);
            }
            Set<Point> badFoxLocations = new HashSet<>();
            pieces.entrySet().stream()
                    .filter(entry -> entry.getValue() instanceof Fox)
                    .map(entry -> Map.entry(entry.getKey(), (Fox) entry.getValue()))
                    .filter(entry -> {
                        switch (entry.getValue().getDirection()) {
                            case MINUS_X -> {
                                return !pieces.containsKey(new Point(entry.getKey().x + 1, entry.getKey().y));
                            }
                            case MINUS_Y -> {
                                return !pieces.containsKey(new Point(entry.getKey().x, entry.getKey().y + 1));
                            }
                            case PLUS_X -> {
                                return !pieces.containsKey(new Point(entry.getKey().x - 1, entry.getKey().y));
                            }
                            case PLUS_Y -> {
                                return !pieces.containsKey(new Point(entry.getKey().x, entry.getKey().y - 1));
                            }
                            default -> {
                                return false;
                            }
                        }
                    }).forEach(entry -> badFoxLocations.add(entry.getKey()));
            pieces.entrySet().stream()
                    .filter(entry -> entry.getValue() instanceof Fox)
                    .map(entry -> Map.entry(entry.getKey(), (Fox) entry.getValue()))
                    .filter(entry -> holes.contains(entry.getKey()) ||
                            raisedSquares.contains(entry.getKey()))
                    .forEach(entry -> {
                        badFoxLocations.add(entry.getKey());
                        switch (entry.getValue().getDirection()) {
                            case MINUS_X -> badFoxLocations.add(new Point(entry.getKey().x + 1, entry.getKey().y));
                            case MINUS_Y -> badFoxLocations.add(new Point(entry.getKey().x, entry.getKey().y + 1));
                            case PLUS_X -> badFoxLocations.add(new Point(entry.getKey().x - 1, entry.getKey().y));
                            case PLUS_Y -> badFoxLocations.add(new Point(entry.getKey().x, entry.getKey().y - 1));
                        }
                    });
            badFoxLocations.forEach(pieces::remove);
            pieces.keySet().forEach(point -> updateMax(max, point));
            return new Board(board, pieces, max);
        }
    }
}
