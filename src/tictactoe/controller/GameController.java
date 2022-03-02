package tictactoe.controller;

import tictactoe.Helper;
import tictactoe.model.Model;

import java.util.Random;

public abstract class GameController {
    public static final int HUMAN        = 0;
    public static final int COMPUTER     = 1;
    public static final int EMPTY        = 2;
    public static final int HUMAN_WIN    = 0;
    public static final int DRAW         = 1;
    public static final int UNCLEAR      = 2;
    public static final int COMPUTER_WIN = 3;

    // Bridge pattern: abstract Controller has an abstract Model
    protected Model board;

    public static String NAME;
    protected int side;
    protected int position = UNCLEAR;
    protected char computerChar, humanChar;

    public GameController(Model board) {
        this.board = board;
    }

    public void init() {
        board.clear();
        Random random = new Random();
        side = random.nextInt(2);
        initSide();
    }

    public void setComputerPlays() {
        this.side = COMPUTER;
        initSide();
    }

    public void setHumanPlays() {
        this.side = HUMAN;
        initSide();
    }

    public boolean computerPlays()
    {
        return side == COMPUTER;
    }

    public boolean gameOver() {
        this.position = board.positionValue();
        return this.position != UNCLEAR;
    }

    public String winner() {
        if      (this.position==COMPUTER_WIN) return "I win!";
        else if (this.position==HUMAN_WIN   ) return "You win!";
        else                                  return "Nobody wins...";
    }

    public void printBoard() {
        System.out.println(board);
    }

    public Model getBoard() {
        return board;
    }

    public void playMove(int move) {
        // Put X or O, or B or W on chosen tile, or pass
        if (move != -1) {
            board.putMove(move, this.side);
        }
        // Switch side
        if (side == COMPUTER) {
            this.side = HUMAN;
        }  else {
            this.side = COMPUTER;
        }
    }

    public abstract void initSide();
    public abstract int chooseMove();
}
