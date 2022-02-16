package tictactoe.model;

import tictactoe.Helper;
import tictactoe.controller.GameController;

import java.util.List;

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
        int move;
        List<Integer> possibleMoves = getAvailableMoves();
        String returnString = "    ";
        for (int x = 0; x < this.horizontalSize; x++) {
            returnString += x + " ";
        }
        returnString += "\n---";
        for (int x = 0; x < this.horizontalSize; x++) {
            returnString += "--";
        }
        returnString += "\n";
        for (int y = 0; y < this.verticalSize; y++) {
            returnString += y + " |";
            for (int x = 0; x < this.horizontalSize; x++) {
                if (board[x][y] == GameController.EMPTY) {
                    move = Helper.coordsToMove(x, y, this.horizontalSize);
                    if (possibleMoves.contains(move)) {
                        returnString += " *";
                    } else {
                        returnString += " .";
                    }
                } else if (board[x][y] == GameController.HUMAN) {
                    returnString += " " + humanChar;
                } else {
                    returnString += " " + computerChar;
                }

            }
            returnString += "\n";
        }
        return returnString;
    }

    public abstract boolean isAWin(int side);
    public abstract int canWin(int side);
    public abstract List<Integer> getAvailableMoves();
    public abstract boolean moveOk(int move);
}
