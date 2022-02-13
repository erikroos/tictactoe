package tictactoe.model;

import tictactoe.controller.GameController;

/**
 * Code (c) Hanzehogeschool Groningen
 */
public abstract class Model {
    public int horizontalSize;
    public int verticalSize;
    protected int[][] board;
    protected char computerChar, humanChar;

    public Model(int horizontalSize, int verticalSize) {
        this.horizontalSize = horizontalSize;
        this.verticalSize = verticalSize;
        board = new int[this.horizontalSize][this.verticalSize];
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

    public abstract boolean isAWin(int side);

    public abstract int canWin(int side);

    // Compute static value of current position (win, draw, etc.)
    public int positionValue() {
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
        for (int i = 0; i < this.horizontalSize; i++) {
            for (int j = 0; j < this.verticalSize; j++) {
                board[i][j] = GameController.EMPTY;
            }
        }
    }

    private boolean isFull() {
        for (int i = 0; i < this.horizontalSize; i++) {
            for (int j = 0; j < this.verticalSize; j++) {
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
        String returnString = "    ";
        for (int j = 0; j < this.horizontalSize; j++) {
            returnString += j + " ";
        }
        returnString += "\n---";
        for (int j = 0; j < this.horizontalSize; j++) {
            returnString += "--";
        }
        returnString += "\n";
        for (int i = 0; i < this.verticalSize; i++) {
            returnString += i + " |";
            for (int j = 0; j < this.horizontalSize; j++) {
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
