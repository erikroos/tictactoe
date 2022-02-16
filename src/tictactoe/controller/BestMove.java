package tictactoe.controller;

/**
 * Simple container class to store optimal move that the AI can make.
 */
public class BestMove {
    int square;
    int val;

    public BestMove(int v) {
        this(0, v);
    }

    public BestMove(int move, int value) {
        square = move;
        val = value;
    }
}
