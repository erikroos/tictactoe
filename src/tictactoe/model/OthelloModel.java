package tictactoe.model;

import tictactoe.controller.GameController;

public class OthelloModel extends Model {
    public OthelloModel(int horizontalSize, int verticalSize) {
        super(horizontalSize, verticalSize);
    }

    @Override
    public boolean isAWin(int side) {
        return false;
    }

    @Override
    public int canWin(int side) {
        return 0;
    }
}
