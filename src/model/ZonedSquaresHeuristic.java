package model;

import controller.GameController;

public class ZonedSquaresHeuristic implements Heuristic {

    private int[][] squareValues = {
            {10, -1, 5, 1},
            {-1, -1, 0, 0},
            {5,   0, 2, 2},
            {1,   0, 2, 1}
    };

    private int[] getSquareValueIndices(int x, int y) {
        int[] indices = new int[2];
        indices[0] = (int)(-Math.abs(x - 3.5) + 3.5);
        indices[1] = (int)(-Math.abs(y - 3.5) + 3.5);
        return indices;
    }

    /**
     * Evaluates board in such a way that different positions have different values.
     * E.g. corners are valued highest.
     *
     * @param board
     * @param side
     * @return difference in squares, weighted by value
     */
    @Override
    public int evaluateBoard(Model board, int side) {
        int[] indices;
        int sideScore = 0;
        int oppScore = 0;

        for (int x = 0; x < board.horizontalSize; x++) {
            for (int y = 0; y < board.verticalSize; y++) {
                if (board.getContents(x, y) == GameController.EMPTY) {
                    continue;
                }
                indices = getSquareValueIndices(x, y);
                if (board.getContents(x, y) == side) {
                    sideScore += squareValues[indices[0]][indices[1]];
                } else {
                    oppScore += squareValues[indices[0]][indices[1]];
                }
            }
        }
        return sideScore - oppScore;
    }
}
