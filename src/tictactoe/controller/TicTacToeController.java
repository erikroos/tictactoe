package tictactoe.controller;

import tictactoe.model.TicTacToeModel;

import java.util.*;

/**
 * Code (c) Hanzehogeschool Groningen
 */
public class TicTacToeController extends GameController
{
	public TicTacToeController(TicTacToeModel board) {
		super(board);
		NAME = "Tic Tac Toe";
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

	// Check if move is legal
	public boolean moveOk(int move) {
		int maxSquare = this.board.horizontalSize * this.board.verticalSize;
		return (move >= 0 && move < maxSquare && board.getContents(move / this.board.horizontalSize, move % this.board.horizontalSize) == EMPTY);
	}

	public int chooseMove() {
		BestMove best = chooseMove(COMPUTER);
	    return best.row * 3 + best.column;
    }

	/**
	 * Find optimal move using the Minimax algorithm.
	 *
	 * @param side
	 * @return BestMove
	 */
	protected BestMove chooseMove(int side) {
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
		// TODO add pruning
		for (int move : getAvailableMoves()) {
			// Try this move for the current player
			board.putMove(move / 3, move % 3, side);
			// Now see what the opponent is going to do
			reply = chooseMove(opp); // Recursion!
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
			board.putMove(move / this.board.horizontalSize, move % this.board.horizontalSize, EMPTY);
		}
		return new BestMove(value, bestRow, bestColumn);
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
			return new BestMove(TicTacToeController.UNCLEAR, ourMove / this.board.horizontalSize, ourMove % this.board.horizontalSize);
		}

		// Rule 2: Prevent opponent from making N-in-a-row (using canWin with opp)
		int oppMove = board.canWin(opp);
		if (oppMove > -1) {
			return new BestMove(TicTacToeController.UNCLEAR, oppMove / this.board.horizontalSize, oppMove % this.board.horizontalSize);
		}

		// Rule 3: Take a free square
		ourMove = getAvailableMoves().get(0);
		return new BestMove(TicTacToeController.UNCLEAR, ourMove / this.board.horizontalSize, ourMove % this.board.horizontalSize);
	}

    public List<Integer> getAvailableMoves() {
		List<Integer> moves = new ArrayList<>();
		// Find all clear squares: start with the corners, then center (4), and then the rest
		Integer[] squaresArray = {0, 2, 6, 8, 4, 1, 3, 5, 7};
		for (int pos : squaresArray) {
			if (board.getContents(pos / this.board.horizontalSize, pos % this.board.horizontalSize) == EMPTY) {
				moves.add(pos);
			}
		}
		return moves;
	}
}