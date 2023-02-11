package model;

import helper.Helper;
import controller.GameController;

import java.util.ArrayList;
import java.util.List;

public class TicTacToeModel extends Model {
    public TicTacToeModel(int horizontalSize, int verticalSize) {
        super(horizontalSize, verticalSize);
    }

    public TicTacToeModel(Model that) {
        super(that);
    }

    @Override
    public int positionValue(boolean boardComplete) {
        return super.positionValue();
    }

    // Returns whether 'side' has won in this position
    // TODO make completely size-independent
    public boolean isAWin(int side)
    {
        // Check the three horizontals
        for (int i = 0; i < this.horizontalSize; i++) {
            if (board[i][0] == side && board[i][1] == side && board[i][2] == side) {
                return true;
            }
        }
        // Check the three verticals
        for (int i = 0; i < this.verticalSize; i++) {
            if (board[0][i] == side && board[1][i] == side && board[2][i] == side) {
                return true;
            }
        }
        // Check the two diagonals
        if (board[0][0] == side && board[1][1] == side && board[2][2] == side) {
            return true;
        }
        if (board[0][2] == side && board[1][1] == side && board[2][0] == side) {
            return true;
        }
        return false;
    }

    public List<Integer> getAvailableMoves(int side) {
        List<Integer> moves = new ArrayList<>();
        // Find all clear squares: start with the corners, then center (4), and then the rest
        Integer[] squaresArray = {0, 2, 6, 8, 4, 1, 3, 5, 7};
        for (int pos : squaresArray) {
            if (moveOk(pos, side)) {
                moves.add(pos);
            }
        }
        return moves;
    }

    @Override
    public boolean moveOk(int move, int side) {
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
        return true;
    }
}
