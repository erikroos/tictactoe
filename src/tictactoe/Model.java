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
            if (board[i][0] == side && board[i][1] == side && board[i][2] == Controller.EMPTY) {
                return i * 3 + 2;
            }
        }
        // TODO other two cases

        // Check the three verticals
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == side && board[1][i] == side && board[2][i] == Controller.EMPTY) {
                return i * 2 + 2;
            }
        }
        // TODO other two cases

        // Check the two diagonals
        if (board[0][0] == side && board[1][1] == side && board[2][2] == Controller.EMPTY) {
            return 8;
        }
        // TODO other two cases
        if (board[0][2] == side && board[1][1] == side && board[2][0] == Controller.EMPTY) {
            return 6;
        }
        // TODO other two cases

        // No way to make 3-in-a-row...
        return -1;
    }

    // Compute static value of current position (win, draw, etc.)
    protected int positionValue( )
    {
        if (isAWin(Controller.HUMAN)) {
            return Controller.HUMAN_WIN;
        }
        if (isAWin(Controller.COMPUTER)) {
            return Controller.COMPUTER_WIN;
        }
        if (isFull()) {
            return Controller.DRAW;
        }
        return Controller.UNCLEAR;
    }

    public void clear()
    {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = Controller.EMPTY;
            }
        }
    }

    private boolean isFull()
    {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == Controller.EMPTY) {
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
        return board[row][column] == Controller.EMPTY;
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
                if (board[i][j] == Controller.EMPTY) {
                    returnString += ".";
                } else if (board[i][j] == Controller.HUMAN) {
                    returnString += Controller.humanChar;
                } else {
                    returnString += Controller.computerChar;
                }

            }
            returnString += "\n";
        }
        return returnString;
    }
}
