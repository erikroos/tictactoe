package model;

import helper.Helper;
import controller.GameController;

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

    public boolean moveOk(int move, int side) {
        if (move == -1) {
            return true; // Pass is always OK
        }
        int maxSquare = this.horizontalSize * this.verticalSize;
        // Check 1: move is within board
        if (move < 0 || move >= maxSquare) {
            return false;
        }
        // Check 2: square is empty
        int[] coords = Helper.moveToCoords(move, this.horizontalSize);
        if (getContents(coords[0], coords[1]) != GameController.EMPTY) {
            return false;
        }
        // Check 3: square borders on stone of opponent
        List<int[]> borderSquares = getBorderSquares(side, coords);
        if (borderSquares.size() == 0) {
            return false;
        }
        // Check 4: causes flip
        for (int[] oppCoords : borderSquares) {
            if (checkSquareForFlip(coords, oppCoords, side)) {
                return true;
            }
        }
        // No flip apparently
        return false;
    }

    public List<Integer> getAvailableMoves(int side) {
        int move;
        List<Integer> moves = new ArrayList<>();
        for (int x = 0; x < this.horizontalSize; x++) {
            for (int y = 0; y < this.verticalSize; y++) {
                move = Helper.coordsToMove(x, y, this.horizontalSize);
                if (moveOk(move, side)) {
                    moves.add(move);
                }
            }
        }
        return moves;
    }

    private List<int[]> getBorderSquares(int side, int[] coords) {
        int opp = (side == GameController.COMPUTER) ? GameController.HUMAN : GameController.COMPUTER;
        List<int[]> borderSquares = new ArrayList<int[]>();
        for (int x = Math.max(0, coords[0] - 1); x <= Math.min(this.horizontalSize - 1, coords[0] + 1); x++) {
            for (int y = Math.max(0, coords[1] - 1); y <= Math.min(this.verticalSize - 1, coords[1] + 1); y++) {
                if (x == coords[0] && y == coords[1]) { // skip square itself
                    continue;
                }
                if (getContents(x, y) == opp) {
                    int[] oppSquare = new int[2];
                    oppSquare[0] = x;
                    oppSquare[1] = y;
                    borderSquares.add(oppSquare);
                }
            }
        }
        return borderSquares;
    }

    private boolean checkSquareForFlip(int[] coords, int[] oppCoords, int side) {
        int dX = oppCoords[0] - coords[0];
        int dY = oppCoords[1] - coords[1];
        int xNext = oppCoords[0];
        int yNext = oppCoords[1];

        while (true) {
            xNext += dX;
            yNext += dY;
            // Out of bounds?
            if (xNext < 0 || xNext >= this.horizontalSize || yNext < 0 || yNext >= this.verticalSize) {
                break;
            }
            // Empty?
            if (getContents(xNext, yNext) == GameController.EMPTY) {
                break;
            }
            // Our own? Flippable!
            if (getContents(xNext, yNext) == side) {
                return true;
            }
            // Opponent, so keep going
        }
        return false;
    }

    private void flip(int[] coords, int[] oppCoords, int side) {
        int dX = oppCoords[0] - coords[0];
        int dY = oppCoords[1] - coords[1];
        int xNext = oppCoords[0];
        int yNext = oppCoords[1];

        while (true) {
            // Our own? Done!
            if (getContents(xNext, yNext) == side) {
                return;
            }
            // Opponent, so flip
            int move = Helper.coordsToMove(xNext, yNext, this.horizontalSize);
            super.putMove(move, side);
            // Next
            xNext += dX;
            yNext += dY;
        }
    }

    public void putMove(int move, int side) {
        super.putMove(move, side);
        // Perform flip(s)
        int[] coords = Helper.moveToCoords(move, this.horizontalSize);
        List<int[]> borderSquares = getBorderSquares(side, coords);
        for (int[] oppCoords : borderSquares) {
            if (checkSquareForFlip(coords, oppCoords, side)) {
                flip(coords, oppCoords, side);
            }
        }
    }
}
