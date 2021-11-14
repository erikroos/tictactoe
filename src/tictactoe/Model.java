package tictactoe;

/**
 * Code (c) Hanzehogeschool Groningen
 */
public class Model {
    private int[][] board;

    public Model() {
        board = new int[3][3];
    }

    public void putMove(int x, int y, int side) {
        board[x][y] = side;
    }

    public int getContents(int x, int y) {
        return board[x][y];
    }

    // Returns whether 'side' has won in this position
    protected boolean isAWin(int side)
    {
        // Check the three horizontals
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == side && board[i][1] == side && board[i][2] == side) {
                return true;
            }
        }
        // Check the three verticals
        for (int i = 0; i < 3; i++) {
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
    protected int canWin(int side)
    {
        // Check the three horizontals
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == side && board[i][1] == side && board[i][2] == TicTacToeController.EMPTY) {
                return i * 3 + 2; // 2, 5 or 8
            }
            if (board[i][0] == side && board[i][1] == TicTacToeController.EMPTY && board[i][2] == side) {
                return i * 3 + 1;
            }
            if (board[i][0] == TicTacToeController.EMPTY && board[i][1] == side && board[i][2] == side) {
                return i * 3;
            }
        }

        // Check the three verticals
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == side && board[1][i] == side && board[2][i] == TicTacToeController.EMPTY) {
                return 6 + i; // 6, 7 or 8
            }
            if (board[0][i] == side && board[1][i] == TicTacToeController.EMPTY && board[2][i] == side) {
                return 3 + i; // 3, 4 or 5
            }
            if (board[0][i] == TicTacToeController.EMPTY && board[1][i] == side && board[2][i] == side) {
                return i; // 0, 1 or 2
            }
        }

        // Check the two diagonals
        // Top left to bottom right
        if (board[0][0] == side && board[1][1] == side && board[2][2] == TicTacToeController.EMPTY) {
            return 8;
        }
        if (board[0][0] == side && board[1][1] == TicTacToeController.EMPTY && board[2][2] == side) {
            return 4;
        }
        if (board[0][0] == TicTacToeController.EMPTY && board[1][1] == side && board[2][2] == side) {
            return 0;
        }
        // Bottom left to top right
        if (board[0][2] == side && board[1][1] == side && board[2][0] == TicTacToeController.EMPTY) {
            return 6;
        }
        if (board[0][2] == side && board[1][1] == TicTacToeController.EMPTY && board[2][0] == side) {
            return 4;
        }
        if (board[0][2] == TicTacToeController.EMPTY && board[1][1] == side && board[2][0] == side) {
            return 2;
        }

        // No way to make 3-in-a-row...
        return -1;
    }

    // Compute static value of current position (win, draw, etc.)
    protected int positionValue( )
    {
        if (isAWin(TicTacToeController.HUMAN)) {
            return TicTacToeController.HUMAN_WIN;
        }
        if (isAWin(TicTacToeController.COMPUTER)) {
            return TicTacToeController.COMPUTER_WIN;
        }
        if (isFull()) {
            return TicTacToeController.DRAW;
        }
        return TicTacToeController.UNCLEAR;
    }

    public void clear()
    {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = TicTacToeController.EMPTY;
            }
        }
    }

    private boolean isFull()
    {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == TicTacToeController.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    // Play a move, possibly clearing a square
    private void place( int row, int column, int piece )
    {
        board[row][column] = piece;
    }

    private boolean squareIsEmpty( int row, int column )
    {
        return board[row][column] == TicTacToeController.EMPTY;
    }

    /**
     * Gives a textual representation of the TTT board, for View to render.
     * @return String
     */
    public String toString()
    {
        String returnString = "";
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == TicTacToeController.EMPTY) {
                    returnString += ".";
                } else if (board[i][j] == TicTacToeController.HUMAN) {
                    returnString += TicTacToeController.humanChar;
                } else {
                    returnString += TicTacToeController.computerChar;
                }

            }
            returnString += "\n";
        }
        return returnString;
    }
}
