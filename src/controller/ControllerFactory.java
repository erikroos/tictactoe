package controller;

import model.*;

public class ControllerFactory {
    public static GameController getController(String game) throws ClassNotFoundException {
        switch (game) {
            case "tictactoe":
                return new TicTacToeController(new TicTacToeModel(3, 3), new MostSquaresHeuristic());
            case "othello":
                return new OthelloController(new OthelloModel(8, 8), new ZonedSquaresHeuristic());
            default:
                throw new ClassNotFoundException("No controller found for game type " + game);
        }
    }
}
