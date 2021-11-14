package tictactoe;

public interface GameController {
    public void init();
    public void setComputerPlays();
    public void setHumanPlays();
    public int chooseMove(boolean useAI);
    public void playMove(int move);
    public void printBoard();
    public Model getBoard();
}
