package controller;

import model.TicTacToeModel;
import model.MostSquaresHeuristic;

/**
 * Code (c) Hanzehogeschool Groningen
 */
public class TicTacToeController extends GameController
{
	public TicTacToeController(TicTacToeModel board) {
		super(board, 9, new MostSquaresHeuristic());
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
}
