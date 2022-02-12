package tictactoe;

public class OthelloController extends GameController {
    public OthelloController(Model board) {
        super(board);
        NAME = "Othello";
    }

    @Override
    public void init() {

    }

    @Override
    public void setComputerPlays() {

    }

    @Override
    public void setHumanPlays() {

    }

    @Override
    public boolean computerPlays() {
        return false;
    }

    @Override
    public int chooseMove(boolean useAI) {
        return 0;
    }

    @Override
    public void playMove(int move) {

    }

    @Override
    public void printBoard() {

    }

    @Override
    public Model getBoard() {
        return null;
    }

    @Override
    public boolean gameOver() {
        return false;
    }

    @Override
    public String winner() {
        return null;
    }

    @Override
    public boolean moveOk(int move) {
        return false;
    }

    @Override
    public int coordsToNUmber(int x, int y) {
        return 0;
    }
}
