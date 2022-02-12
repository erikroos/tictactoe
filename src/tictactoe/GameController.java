package tictactoe;

public interface GameController { // TODO: abstract superclass?
    public void init();
    public void setComputerPlays();
    public void setHumanPlays();
    public boolean computerPlays();
    public int chooseMove(boolean useAI);
    public void playMove(int move);
    public void printBoard();
    public Model getBoard();
    public boolean gameOver();
    public String winner();
    public boolean moveOk(int move);
}
