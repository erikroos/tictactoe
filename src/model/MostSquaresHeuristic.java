package model;

import controller.GameController;

public class MostSquaresHeuristic implements Heuristic {

    /**
     * Evaluates board based on who has the most squares.
     * Outcome is positive if 'side' has more stones, otherwise negative.
     * @param board
     * @param side
     * @return difference in squares
     */
    @Override
    public int evaluateBoard(Model board, int side) {
        int opp = (side == GameController.COMPUTER) ? GameController.HUMAN : GameController.COMPUTER;
        return board.countSquares(side) - board.countSquares(opp);
    }
}
