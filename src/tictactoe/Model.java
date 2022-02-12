package tictactoe;

/**
 * Code (c) Hanzehogeschool Groningen
 */
public class Model {
    private int[][] board;
    private char computerChar, humanChar;

    public Model() {
        board = new int[3][3];
    }

    public void setChars(char computerChar, char humanChar) {
        this.computerChar = computerChar;
        this.humanChar = humanChar;
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
        for (int i = 0; i < 3; i++) {
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

        // No way to make 3-in-a-row...
        return -1;
    }

    // Compute static value of current position (win, draw, etc.)
    protected int positionValue() {
        if (isAWin(GameController.HUMAN)) {
            return GameController.HUMAN_WIN;
        }
        if (isAWin(GameController.COMPUTER)) {
            return GameController.COMPUTER_WIN;
        }
        if (isFull()) {
            return GameController.DRAW;
        }
        return GameController.UNCLEAR;
    }

    public void clear() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = GameController.EMPTY;
            }
        }
    }

    private boolean isFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == GameController.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Gives a textual representation of the board, for View to render.
     * @return String
     */
    public String toString()
    {
        String returnString = "    0 1 2\n---------\n";
        for (int i = 0; i < 3; i++) {
            returnString += i + " |";
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == GameController.EMPTY) {
                    returnString += " .";
                } else if (board[i][j] == GameController.HUMAN) {
                    returnString += " " + humanChar;
                } else {
                    returnString += " " + computerChar;
                }

            }
            returnString += "\n";
        }
        return returnString;
    }
}
