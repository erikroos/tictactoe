package tictactoe.model;

import tictactoe.Helper;
import tictactoe.controller.GameController;

import java.util.ArrayList;
import java.util.List;

public class OthelloModel extends Model {
    public OthelloModel(int horizontalSize, int verticalSize) {
        super(horizontalSize, verticalSize);
    }

    @Override
    public boolean isAWin(int side) {
        return false;
    }

    @Override
    public int canWin(int side) {
        return 0;
    }

    public boolean moveOk(int move) {
        int maxSquare = this.horizontalSize * this.verticalSize;
        // Check 1: move is within board
        if (move < 0 && move >= maxSquare) {
            return false;
        }
        // Check 2: square is empty
        int[] coords = Helper.moveToCoords(move, this.horizontalSize);
        if (getContents(coords[0], coords[1]) != GameController.EMPTY) {
            return false;
        }
        // Check 3: square borders on stone of opponent
        // TODO
        // Check 4: causes flip
        // TODO
        return true;
    }

    public List<Integer> getAvailableMoves() {
        int move;
        List<Integer> moves = new ArrayList<>();
        for (int x = 0; x < this.horizontalSize; x++) {
            for (int y = 0; y < this.verticalSize; y++) {
                move = Helper.coordsToMove(x, y, this.horizontalSize);
                if (moveOk(move)) {
                    moves.add(move);
                }
            }
        }
        return moves;
    }
}
