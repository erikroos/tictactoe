package tictactoe.controller;

import tictactoe.Helper;
import tictactoe.model.Model;
import tictactoe.model.OthelloModel;
import tictactoe.model.TicTacToeModel;

import java.util.List;
import java.util.Random;

public abstract class GameController {
    public static final int HUMAN        = 0;
    public static final int COMPUTER     = 1;
    public static final int EMPTY        = 2;
    public static final int HUMAN_WIN    = 0;
    public static final int DRAW         = 1;
    public static final int UNCLEAR      = 2;
    public static final int COMPUTER_WIN = 3;

    public static int MAX_DEPTH;

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
        // Put X or O, or B or W, on chosen tile, or pass
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

    public int chooseMove() {
        BestMove best = chooseMove(
                COMPUTER,
                MAX_DEPTH,
                getBoardCopy(this.board),
                Integer.MIN_VALUE,
                Integer.MAX_VALUE);
        return best.square;
    }

    /**
     * Find optimal move using the Minimax algorithm.
     *
     * @param side
     * @return BestMove
     */
    protected BestMove chooseMove(int side, int depth, Model boardCopy, int alpha, int beta) {
        int opp = (side == COMPUTER) ? HUMAN : COMPUTER; // The other side (in the first call of this method this is always HUMAN)
        BestMove reply; // Opponent's best reply
        int simpleEval; // Result of an immediate evaluation
        // For storing the best result so far:
        int maxMove = 0;
        int value = (side == COMPUTER) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        // Base case 1: board is full, we can see directly what the score is
        simpleEval = boardCopy.positionValue();
        if (simpleEval != UNCLEAR) {
            return new BestMove(simpleEval);
        }
        // Base case 2: max depth reached
        if (depth == 0) {
            List<Integer> moves = boardCopy.getAvailableMoves(side);
            if (moves.size() == 0) {
                return new BestMove( -1, UNCLEAR);
            }
            int maxHeurEval = (side == COMPUTER) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            int maxHeurMove = -1;
            int heurEval;
            for (int move : moves) {
                heurEval = boardCopy.positionValue(true);
                if (side == COMPUTER && heurEval > maxHeurEval) {
                    maxHeurEval = heurEval;
                    maxHeurMove = move;
                }
                if (side == HUMAN && heurEval < maxHeurEval) {
                    maxHeurEval = heurEval;
                    maxHeurMove = move;
                }
            }
            return new BestMove(maxHeurMove, maxHeurEval);
        }

        // Backtrack case: try all open moves and see which is best
        for (int move : boardCopy.getAvailableMoves(side)) {
            Model freshBoardCopy = getBoardCopy(boardCopy);
            // Try this move for the current player
            freshBoardCopy.putMove(move, side);
            // Now see what the opponent is going to do
            reply = chooseMove(opp, depth - 1, freshBoardCopy, alpha, beta); // Recursion
            if (side == HUMAN) {
                // Human, minimizing
                if (reply.val < value) {
                    value = reply.val;
                    maxMove = move;
                }
                if (value < beta) {
                    beta = value;
                }
            } else {
                // Computer, maximizing
                if (reply.val > value) {
                    value = reply.val;
                    maxMove = move;
                }
                if (value > alpha) {
                    alpha = value;
                }
            }
            // Cut this whole branch if we know it will never be chosen
            if (alpha >= beta) {
                break;
            }
            // No need to undo move because we used a copy of the board
        }
        return new BestMove(maxMove, value);
    }

    private Model getBoardCopy(Model boardCopy) {
        Model newBoardCopy;
        if (boardCopy.getClass().equals(TicTacToeModel.class)) {
            newBoardCopy = new TicTacToeModel(boardCopy); // Deep copy using Copy constructor
        } else {
            newBoardCopy = new OthelloModel(boardCopy); // Deep copy using Copy constructor
        }
        return newBoardCopy;
    }

    public abstract void initSide();
}
