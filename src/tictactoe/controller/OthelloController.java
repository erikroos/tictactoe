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
        board.putMove(4, 3, this.side);
        board.putMove(3, 4, this.side);
        // White on center top left - bottom right
        int opp = (this.side == COMPUTER) ? HUMAN : COMPUTER;
        board.putMove(3, 3, opp);
        board.putMove(4, 4, opp);
    }

    public List<Integer> getAvailableMoves() {
        int move;
        List<Integer> moves = new ArrayList<>();
        for (int x = 0; x < this.board.horizontalSize; x++) {
            for (int y = 0; y < this.board.verticalSize; y++) {
                move = x * this.board.horizontalSize + y;
                if (moveOk(move)) {
                    moves.add(move);
                }
            }
        }
        return moves;
    }

    public int chooseMove() {
        List<Integer> moves = getAvailableMoves();
        Random random = new Random();
        int randIndex = random.nextInt(moves.size()); // TODO smarter
        return moves.get(randIndex);
    }

    public boolean moveOk(int move) {
        int maxSquare = this.board.horizontalSize * this.board.verticalSize;
        return (move >= 0 && move < maxSquare && board.getContents(move / this.board.horizontalSize, move % this.board.horizontalSize) == EMPTY);
        // TODO add extra checks for Othello
    }
}
