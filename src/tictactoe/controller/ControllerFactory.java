package tictactoe.controller;

import tictactoe.model.OthelloModel;
import tictactoe.model.TicTacToeModel;

public class ControllerFactory {
    public static GameController getController(String game) throws ClassNotFoundException {
        switch (game) {
            case "tictactoe":
                return new TicTacToeController(new TicTacToeModel(3, 3));
            case "othello":
                return new OthelloController(new OthelloModel(8, 8));
            default:
                throw new ClassNotFoundException("No controller found for game type " + game);
        }
    }
}
