package tictactoe.controller;

import tictactoe.model.Model;
import tictactoe.model.OthelloModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OthelloController extends GameController {
    public OthelloController(OthelloModel board) {
        super(board);
        NAME = "Othello";
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

    public int chooseMove() {
        List<Integer> moves = board.getAvailableMoves(COMPUTER);
        if (moves.size() == 0) {
            return -1;
        }
        Random random = new Random();
        int randIndex = random.nextInt(moves.size()); // TODO smarter (by using generic chooseMove in superclass)
        return moves.get(randIndex);
    }
}
