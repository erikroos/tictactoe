package tictactoe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Code (c) Hanzehogeschool Groningen
 */
class TicTacToe
{
	protected static final int HUMAN        = 0;
	protected static final int COMPUTER     = 1;
	public  static final int EMPTY          = 2;

	public  static final int HUMAN_WIN    = 0;
	public  static final int DRAW         = 1;
	public  static final int UNCLEAR      = 2;
	public  static final int COMPUTER_WIN = 3;

	private int[][] board = new int[3][3];
    private Random random = new Random();
	private int side = random.nextInt(2);
	private int position = UNCLEAR;
	private char computerChar, humanChar;

	// Constructor
	public TicTacToe()
	{
		clearBoard();
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
		return side==COMPUTER;
	}

	public int chooseMove()
	{
	    Best best = chooseMove(COMPUTER);
	    return best.row * 3 + best.column;
    }
    
    // Find optimal move
	protected Best chooseMove(int side)
	{
		int opp = (side == COMPUTER) ? HUMAN : COMPUTER; // The other side (in the first call of this method this is always HUMAN)
		Best reply;           // Opponent's best reply
		int simpleEval;       // Result of an immediate evaluation
		// For storing the best result so far:
		int bestRow = 0;
		int bestColumn = 0;
		int value = (side == COMPUTER) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

		// Base case: board is full, we can see directly what the score is
		simpleEval = positionValue();
		if (simpleEval != UNCLEAR) {
			return new Best(simpleEval);
		}

		// TODO extra step: for efficiency, use some heuristics like always take central square if it is free

		// Backtrack case: try all open moves and see which is best
		for (int move : getNextMoves()) {
			// Try this move for the current player
			board[move / 3][move % 3] = side;
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
			board[move / 3][move % 3] = EMPTY;
		}
		return new Best(value, bestRow, bestColumn);
    }

    private List<Integer> getNextMoves() {
		List<Integer> moves = new ArrayList<>();
		// Find all clear squares
		for (int pos = 0; pos < 9; pos++) {
			if (board[pos / 3][pos % 3] == EMPTY) {
				moves.add(pos);
			}
		}
		return moves;
	}
   
    // Check if move is legal
    public boolean moveOk(int move)
    {
		return (move >= 0 && move <= 8 && board[move / 3][move % 3] == EMPTY);
    }
    
    // Play move
    public void playMove(int move)
    {
		// Put X or O on chosen tile
		board[move / 3][move % 3] = this.side;
		// Switch side
		if (side == COMPUTER) {
			this.side = HUMAN;
		}  else {
			this.side = COMPUTER;
		}
	}

	// Simple supporting routines

	private void clearBoard( )
	{
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = EMPTY;
			}
		}
	}

	private boolean boardIsFull()
	{
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == EMPTY) {
					return false;
				}
			}
		}
		return true;
	}

	// Returns whether 'side' has won in this position
	protected boolean isAWin(int side)
	{
		// Check the three horizontals
		for (int i = 0; i < 3; i++) {
			if (board[i][0] == side && board[i][1] == side && board[i][2] == side) {
				return true;
			}
		}
	    // Check the three verticals
		for (int i = 0; i < 3; i++) {
			if (board[0][i] == side && board[1][i] == side && board[2][i] == side) {
				return true;
			}
		}
		// Check the two diagonals
		if (board[0][0] == side && board[1][1] == side && board[2][2] == side) {
			return true;
		}
		if (board[0][2] == side && board[1][1] == side && board[2][0] == side) {
			return true;
		}
		return false;
    }

	// Play a move, possibly clearing a square
	private void place( int row, int column, int piece )
	{
		board[row][column] = piece;
	}

	private boolean squareIsEmpty( int row, int column )
	{
		return board[row][column] == EMPTY;
	}

	// Compute static value of current position (win, draw, etc.)
	protected int positionValue( )
	{
		if (isAWin(HUMAN)) {
			return HUMAN_WIN;
		}
		if (isAWin(COMPUTER)) {
			return COMPUTER_WIN;
		}
		if (boardIsFull()) {
			return DRAW;
		}
		return UNCLEAR;
	}

	/**
	 * Gives a textual representation of the TTT board.
	 * @return String
	 */
	public String toString()
	{
		String returnString = "";
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == EMPTY) {
					returnString += ".";
				} else if (board[i][j] == HUMAN) {
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
	    this.position = positionValue();
	    return this.position != UNCLEAR;
    }
    
    public String winner()
    {
        if      (this.position==COMPUTER_WIN) return "computer";
        else if (this.position==HUMAN_WIN   ) return "human";
        else                                  return "nobody";
    }

	protected class Best
    {
       int row;
       int column;
       int val;

       public Best(int v) {
		   this(v, 0, 0);
	   }
      
       public Best(int v, int r, int c) {
		   val = v; row = r; column = c;
	   }
    }
}