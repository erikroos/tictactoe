package tictactoe.model;

import tictactoe.controller.GameController;

public class TicTacToeModel extends Model {
    public TicTacToeModel(int horizontalSize, int verticalSize) {
        super(horizontalSize, verticalSize);
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

    // Returns move that 'side' can make to win in this position
    // TODO make completely size-independent
    public int canWin(int side)
    {
        // Check the three horizontals
        for (int i = 0; i < this.horizontalSize; i++) {
            if (board[i][0] == side && board[i][1] == side && board[i][2] == GameController.EMPTY) {
                return i * 3 + 2; // 2, 5 or 8
            }
            if (board[i][0] == side && board[i][1] == GameController.EMPTY && board[i][2] == side) {
                return i * 3 + 1;
            }
            if (board[i][0] == GameController.EMPTY && board[i][1] == side && board[i][2] == side) {
                return i * 3;
            }
        }

        // Check the three verticals
        for (int i = 0; i < this.verticalSize; i++) {
            if (board[0][i] == side && board[1][i] == side && board[2][i] == GameController.EMPTY) {
                return 6 + i; // 6, 7 or 8
            }
            if (board[0][i] == side && board[1][i] == GameController.EMPTY && board[2][i] == side) {
                return 3 + i; // 3, 4 or 5
            }
            if (board[0][i] == GameController.EMPTY && board[1][i] == side && board[2][i] == side) {
                return i; // 0, 1 or 2
            }
        }

        // Check the two diagonals
        // Top left to bottom right
        if (board[0][0] == side && board[1][1] == side && board[2][2] == GameController.EMPTY) {
            return 8;
        }
        if (board[0][0] == side && board[1][1] == GameController.EMPTY && board[2][2] == side) {
            return 4;
        }
        if (board[0][0] == GameController.EMPTY && board[1][1] == side && board[2][2] == side) {
            return 0;
        }
        // Bottom left to top right
        if (board[0][2] == side && board[1][1] == side && board[2][0] == GameController.EMPTY) {
            return 6;
        }
        if (board[0][2] == side && board[1][1] == GameController.EMPTY && board[2][0] == side) {
            return 4;
        }
        if (board[0][2] == GameController.EMPTY && board[1][1] == side && board[2][0] == side) {
            return 2;
        }

        // No way to make N-in-a-row...
        return -1;
    }
}
