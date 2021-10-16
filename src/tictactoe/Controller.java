package tictactoe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Code (c) Hanzehogeschool Groningen
 */
class Controller
{
	protected static final int HUMAN        = 0;
	protected static final int COMPUTER     = 1;
	public  static final int EMPTY          = 2;

	public  static final int HUMAN_WIN    = 0;
	public  static final int DRAW         = 1;
	public  static final int UNCLEAR      = 2;
	public  static final int COMPUTER_WIN = 3;

	protected Model board = new Model();

    private Random random = new Random();
	private int side = random.nextInt(2);
	private int position = UNCLEAR;
	private char computerChar, humanChar;

	// Constructor
	public Controller()
	{
		board.clear();
		initSide();
	}
	
	private void initSide()
	{
	    if (this.side == COMPUTER) {
			computerChar = 'X';
			humanChar = 'O';
		} else {
			computerChar = 'O';
			humanChar = 'X';
		}
    }
    
    public void setComputerPlays()
    {
        this.side = COMPUTER;
        initSide();
    }
    
    public void setHumanPlays()
    {
        this.side = HUMAN;
        initSide();
    }

	public boolean computerPlays()
	{
		return side == COMPUTER;
	}

	// Check if move is legal
	public boolean moveOk(int move)
	{
		return (move >= 0 && move <= 8 && board.getContents(move / 3, move % 3) == EMPTY);
	}

	public int chooseMove(boolean useAI)
	{
		BestMove best;
		if (useAI) {
			best = chooseMoveAI(COMPUTER);
		} else {
			best = chooseMoveHeur(COMPUTER);
		}
	    return best.row * 3 + best.column;
    }

	/**
	 * Find optimal move using the Minimax algorithm.
	 *
	 * @param side
	 * @return BestMove
	 */
	protected BestMove chooseMoveAI(int side)
	{
		int opp = (side == COMPUTER) ? HUMAN : COMPUTER; // The other side (in the first call of this method this is always HUMAN)
		BestMove reply;       // Opponent's best reply
		int simpleEval;       // Result of an immediate evaluation
		// For storing the best result so far:
		int bestRow = 0;
		int bestColumn = 0;
		int value = (side == COMPUTER) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

		// Base case: board is full, we can see directly what the score is
		simpleEval = board.positionValue();
		if (simpleEval != UNCLEAR) {
			return new BestMove(simpleEval);
		}

		// Backtrack case: try all open moves and see which is best
		for (int move : getNextMoves()) {
			// Try this move for the current player
			board.putMove(move / 3, move % 3, side);
			// Now see what the opponent is going to do
			reply = chooseMoveAI(opp); // Recursion!
			if (side == HUMAN) {
				// Human, minimizing
				if (reply.val < value) {
					value = reply.val;
					bestRow = move / 3;
					bestColumn = move % 3;
				}
			} else {
				// Computer, maximizing
				if (reply.val > value) {
					value = reply.val;
					bestRow = move / 3;
					bestColumn = move % 3;
				}
			}
			// Undo move
			board.putMove(move / 3, move % 3, EMPTY);
		}
		return new BestMove(value, bestRow, bestColumn);
    }

	/**
	 * Find optimal move using simple heuristics.
	 *
	 * @param side
	 * @return BestMove
	 */
	protected BestMove chooseMoveHeur(int side) {
		int opp = (side == COMPUTER) ? HUMAN : COMPUTER; // The other side (in the first call of this method this is always HUMAN)
		BestMove reply;       // Opponent's best reply
		int simpleEval;       // Result of an immediate evaluation
		// For storing the best result so far:
		int bestRow = 0;
		int bestColumn = 0;
		int value = (side == COMPUTER) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

		// Base case: board is full, we can see directly what the score is
		simpleEval = board.positionValue();
		if (simpleEval != UNCLEAR) {
			return new BestMove(simpleEval);
		}

		// TODO
		return null;
	}

    private List<Integer> getNextMoves() {
		List<Integer> moves = new ArrayList<>();
		// Find all clear squares
		for (int pos = 0; pos < 9; pos++) {
			if (board.getContents(pos / 3, pos % 3) == EMPTY) {
				moves.add(pos);
			}
		}
		return moves;
	}
    
    // Play move
    public void playMove(int move)
    {
		// Put X or O on chosen tile
		board.putMove(move / 3, move % 3, this.side);
		// Switch side
		if (side == COMPUTER) {
			this.side = HUMAN;
		}  else {
			this.side = COMPUTER;
		}
	}

	/**
	 * Gives a textual representation of the TTT board, for View to render.
	 * @return String
	 */
	public String toString()
	{
		String returnString = "";
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board.getContents(i, j) == EMPTY) {
					returnString += ".";
				} else if (board.getContents(i, j) == HUMAN) {
					returnString += this.humanChar;
				} else {
					returnString += this.computerChar;
				}

			}
			returnString += "\n";
		}
		return returnString;
	}  
	
	public boolean gameOver()
	{
	    this.position = board.positionValue();
	    return this.position != UNCLEAR;
    }
    
    public String winner()
    {
        if      (this.position==COMPUTER_WIN) return "computer";
        else if (this.position==HUMAN_WIN   ) return "human";
        else                                  return "nobody";
    }
}