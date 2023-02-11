package controller;

import model.TicTacToeModel;

/**
 * Code (c) Hanzehogeschool Groningen
 */
public class TicTacToeController extends GameController
{
	public TicTacToeController(TicTacToeModel board) {
		super(board);
		NAME = "Tic Tac Toe";
		GAMENAME = "tictactoe";
	}
	
	public void initSide() {
	    if (this.side == COMPUTER) {
			computerChar = 'X';
			humanChar = 'O';
		} else {
			computerChar = 'O';
			humanChar = 'X';
		}
		board.setChars(computerChar, humanChar);
    }

	public int chooseMove() {
		BestMove best = chooseMove(COMPUTER);
	    return best.square;
    }

	/**
	 * Find optimal move using the Minimax algorithm.
	 * TODO: move to superclass
	 *
	 * @param side
	 * @return BestMove
	 */
	protected BestMove chooseMove(int side) {
		int opp = (side == COMPUTER) ? HUMAN : COMPUTER; // The other side (in the first call of this method this is always HUMAN)
		BestMove reply; // Opponent's best reply
		int simpleEval; // Result of an immediate evaluation
		// For storing the best result so far:
		int maxMove = 0;
		int value = (side == COMPUTER) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

		// Base case: board is full, we can see directly what the score is
		simpleEval = board.positionValue();
		if (simpleEval != UNCLEAR) {
			return new BestMove(simpleEval);
		}

		// Backtrack case: try all open moves and see which is best
		// TODO add pruning
		for (int move : board.getAvailableMoves(side)) {
			// Try this move for the current player
			board.putMove(move, side);
			// Now see what the opponent is going to do
			reply = chooseMove(opp); // Recursion!
			if (side == HUMAN) {
				// Human, minimizing
				if (reply.val < value) {
					value = reply.val;
					maxMove = move;
				}
			} else {
				// Computer, maximizing
				if (reply.val > value) {
					value = reply.val;
					maxMove = move;
				}
			}
			// Undo move
			board.putMove(move, EMPTY);
		}
		return new BestMove(maxMove, value);
    }

	/**
	 * Find optimal move using simple heuristics.
	 * Not used atm.
	 *
	 * @param side
	 * @return BestMove
	 */
	protected BestMove chooseMoveHeur(int side) {
		int opp = (side == COMPUTER) ? HUMAN : COMPUTER; // The other side (in the first call of this method this is always HUMAN)

		// Base case: board is full, we can see directly what the score is
		int simpleEval = board.positionValue();
		if (simpleEval != UNCLEAR) {
			return new BestMove(simpleEval);
		}

		// Rule 1: Make N-in-a-row if possible (using canWin with side)
		int ourMove = board.canWin(side);
		if (ourMove > -1) {
			return new BestMove(ourMove, TicTacToeController.UNCLEAR);
		}

		// Rule 2: Prevent opponent from making N-in-a-row (using canWin with opp)
		int oppMove = board.canWin(opp);
		if (oppMove > -1) {
			return new BestMove(oppMove, TicTacToeController.UNCLEAR);
		}

		// Rule 3: Take a free square
		ourMove = board.getAvailableMoves(side).get(0);
		return new BestMove(ourMove, TicTacToeController.UNCLEAR);
	}
}
