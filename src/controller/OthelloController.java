package controller;

import model.Model;
import model.OthelloModel;
import model.MostSquaresHeuristic;

import java.util.List;
import java.util.Random;

public class OthelloController extends GameController {
    public OthelloController(Model board) {
        super(board, 6, new MostSquaresHeuristic());
        NAME = "Othello";
        GAMENAME = "reversi";
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
