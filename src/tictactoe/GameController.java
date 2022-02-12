package tictactoe;

public abstract class GameController {
    public static final int HUMAN        = 0;
    public static final int COMPUTER     = 1;
    public static final int EMPTY        = 2;
    public static final int HUMAN_WIN    = 0;
    public static final int DRAW         = 1;
    public static final int UNCLEAR      = 2;
    public static final int COMPUTER_WIN = 3;

    public static String NAME;
    protected Model board;
    protected int side;
    protected int position = UNCLEAR;
    protected char computerChar, humanChar;

    public GameController(Model board) {
        this.board = board;
    }

    public abstract void init();
    public abstract void setComputerPlays();
    public abstract void setHumanPlays();
    public abstract boolean computerPlays();
    public abstract int chooseMove(boolean useAI);
    public abstract void playMove(int move);
    public abstract void printBoard();
    public abstract Model getBoard();
    public abstract boolean gameOver();
    public abstract String winner();
    public abstract boolean moveOk(int move);
    public abstract int coordsToNUmber(int x, int y);
}
