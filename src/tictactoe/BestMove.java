package tictactoe;

/**
 * Simple container class to store optimal move that the AI can make.
 */
public class BestMove {
    int row;
    int column;
    int val;

    public BestMove(int v) {
        this(v, 0, 0);
    }

    public BestMove(int v, int r, int c) {
        val = v; row = r; column = c;
    }
}
