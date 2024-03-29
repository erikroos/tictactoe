package controller;

import model.Model;
import model.Heuristic;

import java.util.Random;

public abstract class GameController {
    public static final int HUMAN        = 0;
    public static final int COMPUTER     = 1;
    public static final int EMPTY        = 2;
    public static final int HUMAN_WIN    = 0;
    public static final int HUMAN_ADV    = 1;
    public static final int DRAW         = 1;
    public static final int UNCLEAR      = 2;
    public static final int COMPUTER_ADV = 2;
    public static final int COMPUTER_WIN = 3;

    protected Model board; // Bridge pattern: abstract Controller has an abstract Model
    private Heuristic heuristic; // Strategy pattern

    public static String NAME;
    public static String GAMENAME;
    protected int side;
    protected int position = UNCLEAR;
    protected char computerChar, humanChar;
    protected int MAX_DEPTH;

    public GameController(Model board, Heuristic heuristic) {
        this.board = board;
        this.heuristic = heuristic;
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
        if (this.position == COMPUTER_WIN) {
            return "computer";
        } else if (this.position == HUMAN_WIN) {
            return "human";
        }
        return "nobody";
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

    public int chooseMove() {
        BestMove best = chooseMove(this.board, COMPUTER, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return best.square;
    }

    /**
     * Find optimal move using the Minimax algorithm.
     *
     * @param side
     * @return BestMove
     */
    protected BestMove chooseMove(Model currentBoard, int side, int depth, int alpha, int beta) {
        // Base case 1: board is full, we can see directly what the score is
        int simpleEvaluationValue = currentBoard.positionValue();
        if (simpleEvaluationValue != UNCLEAR) {
            return new BestMove(simpleEvaluationValue);
        }
        // Base case 2: maximum depth reached, use injected heuristic
        if (depth > MAX_DEPTH) {
            int value;
            int maxMove = -1;
            int maxValue = (side == COMPUTER) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            for (int move : currentBoard.getAvailableMoves(side)) {
                Model boardCopy = currentBoard.makeCopy(); // Work with copy of board so we don't pollute the actual board
                boardCopy.putMove(move, side);
                value = heuristic.evaluateBoard(boardCopy, side);
                if (side == HUMAN) { // Human, minimizing
                    if (value < maxValue) {
                        maxValue = value;
                        maxMove = move;
                    }
                } else { // Computer, maximizing
                    if (value > maxValue) {
                        maxValue = value;
                        maxMove = move;
                    }
                }
            }
            // First, map heuristic outcome (negative or positive number) to Human or Computer advantage
            if (side == HUMAN) { // Human, minimizing
                if (maxValue > 0) {
                    maxValue = HUMAN_ADV;
                } else {
                    maxValue = COMPUTER_ADV;
                }
            } else { // Computer, maximizing
                if (maxValue > 0) {
                    maxValue = COMPUTER_ADV;
                } else {
                    maxValue = HUMAN_ADV;
                }
            }
            return new BestMove(maxMove, maxValue);
        }

        int opp = (side == COMPUTER) ? HUMAN : COMPUTER;
        BestMove opponentReply;
        int maxMove = -1;
        int maxValue = (side == COMPUTER) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        // Backtrack case: try all open moves and see which is best, recursively
        for (int move : currentBoard.getAvailableMoves(side)) {
            Model boardCopy = currentBoard.makeCopy(); // Work with copy of board so we don't pollute the actual board
            boardCopy.putMove(move, side);
            opponentReply = chooseMove(boardCopy, opp, depth + 1, alpha, beta); // Recursion!
            if (side == HUMAN) { // Human, minimizing
                if (opponentReply.val < maxValue) {
                    maxValue = opponentReply.val;
                    maxMove = move;
                    beta = Math.min(beta, maxValue);
                }
            } else { // Computer, maximizing
                if (opponentReply.val > maxValue) {
                    maxValue = opponentReply.val;
                    maxMove = move;
                    alpha = Math.max(alpha, maxValue);
                }
            }
            if (beta <= alpha) {
                break;
            }
        }
        return new BestMove(maxMove, maxValue);
    }

    public abstract void initSide();
}
