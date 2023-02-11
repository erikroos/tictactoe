package controller;

/**
 * Simple container class to store optimal move that the AI can make.
 */
public class BestMove {
    int square;
    int val;

    public BestMove(int value) {
        this(0, value);
    }

    public BestMove(int move, int value) {
        square = move;
        val = value;
    }
}
