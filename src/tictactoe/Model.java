package tictactoe;

public class Model {
    private int[][] board = new int[3][3];

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

    // Compute static value of current position (win, draw, etc.)
    protected int positionValue( )
    {
        if (isAWin(Controller.HUMAN)) {
            return Controller.HUMAN_WIN;
        }
        if (isAWin(Controller.COMPUTER)) {
            return Controller.COMPUTER_WIN;
        }
        if (boardIsFull()) {
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

    private boolean boardIsFull()
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
}
