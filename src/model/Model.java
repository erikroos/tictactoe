package model;

import helper.Helper;
import controller.GameController;

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
        this.board = new int[this.horizontalSize][this.verticalSize];
    }

    public abstract Model makeCopy();

    public void setChars(char computerChar, char humanChar) {
        this.computerChar = computerChar;
        this.humanChar = humanChar;
    }

    public int getContents(int x, int y) {
        return board[x][y];
    }

    public void clear() {
        for (int i = 0; i < this.horizontalSize; i++) {
            for (int j = 0; j < this.verticalSize; j++) {
                board[i][j] = GameController.EMPTY;
            }
        }
    }

    protected boolean isFull() {
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
        List<Integer> possibleMoves = getAvailableMoves(GameController.HUMAN);
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

    public void putMove(int move, int side) {
        int[] coords = Helper.moveToCoords(move, this.horizontalSize);
        int x = coords[0];
        int y = coords[1];
        board[x][y] = side;
    }

    public int countSquares(int side) {
        int counter = 0;
        for (int x = 0; x < this.horizontalSize; x++) {
            for (int y = 0; y < this.verticalSize; y++) {
                if (this.board[x][y] == side) {
                    counter++;
                }
            }
        }
        return counter;
    }

    public abstract boolean isAWin(int side);
    public abstract List<Integer> getAvailableMoves(int side);
    public abstract boolean moveOk(int move, int side);
    public abstract int positionValue();
}
