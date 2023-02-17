package model;

import controller.GameController;

public class MostSquaresHeuristic implements Heuristic {
    @Override
    public int evaluateBoard(Model board, int side) {
        int opp = (side == GameController.COMPUTER) ? GameController.HUMAN : GameController.COMPUTER;

        int sideSquares = board.countSquares(side);
        int oppSquares = board.countSquares(opp);

        if (side == GameController.COMPUTER) { // Computer, maximizing
            if (sideSquares > oppSquares) {
                return GameController.COMPUTER_ADV;
            } else {
                return GameController.HUMAN_ADV;
            }
        } else { // Human, minimizing
            if (sideSquares > oppSquares) {
                return GameController.HUMAN_ADV;
            } else {
                return GameController.COMPUTER_ADV;
            }
        }
    }
}
