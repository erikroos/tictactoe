package tictactoe.controller;

import tictactoe.model.OthelloModel;

public class OthelloController extends GameController {
    public OthelloController(OthelloModel board) {
        super(board);
        NAME = "Othello";
        MAX_DEPTH = 6;
    }

    public void initSide() {
        if (this.side == COMPUTER) {
            // Computer starts, so gets Black
            computerChar = 'B';
            humanChar = 'W';
        } else {
            // Human starts
            humanChar = 'B';
            computerChar = 'W';
        }
        board.setChars(computerChar, humanChar);
        // Black on center top right - bottom left
        board.putMove(28, this.side);
        board.putMove(35, this.side);
        // White on center top left - bottom right
        int opp = (this.side == COMPUTER) ? HUMAN : COMPUTER;
        board.putMove(27, opp);
        board.putMove(36, opp);
    }
}
