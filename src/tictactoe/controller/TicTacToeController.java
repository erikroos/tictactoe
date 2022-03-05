package tictactoe.controller;

import tictactoe.model.TicTacToeModel;

/**
 * Code (c) Hanzehogeschool Groningen
 */
public class TicTacToeController extends GameController
{
	public TicTacToeController(TicTacToeModel board) {
		super(board);
		NAME = "Tic Tac Toe";
		MAX_DEPTH = 9;
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