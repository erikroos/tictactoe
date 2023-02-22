package controller;

import model.Model;
import model.Heuristic;

/**
 * Code (c) Hanzehogeschool Groningen
 */
public class TicTacToeController extends GameController
{
	public TicTacToeController(Model board, Heuristic heuristic) {
		super(board, heuristic);
		MAX_DEPTH = 9;
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
