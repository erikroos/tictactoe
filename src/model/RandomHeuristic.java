package model;

public class RandomHeuristic implements Heuristic {

    /**
     * Evaluates board randomly.
     * @param board
     * @param side
     * @return score, ranging from -10 to +10
     */
    @Override
    public int evaluateBoard(Model board, int side) {
        return (int)(Math.random() * 21 - 10);
    }
}
