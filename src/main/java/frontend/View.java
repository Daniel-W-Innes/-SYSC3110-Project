package frontend;

import backend.helpers.Move;
import backend.helpers.Piece;
import backend.helpers.Point;
import backend.models.Model;

public interface View {
    void update(Model m);
    void update(Move m);
}