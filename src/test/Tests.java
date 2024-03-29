package test;

import org.junit.jupiter.api.Test;
import controller.GameController;
import controller.TicTacToeController;
import model.TicTacToeModel;
import model.MostSquaresHeuristic;

import static org.junit.jupiter.api.Assertions.*;

public class Tests {

    @Test
    void testChooseMove() {
        GameController testTTT = new TicTacToeController(new TicTacToeModel(3, 3), new MostSquaresHeuristic());
        testTTT.init();
        testTTT.setHumanPlays();
        testTTT.playMove(0); // human
        testTTT.playMove(1); // comp
        testTTT.playMove(4); // human
        testTTT.playMove(8); // comp
        testTTT.playMove(6); // human
        testTTT.playMove(3); // comp
        assertTrue(testTTT.chooseMove() == 2);
    }

    @Test
    void testIsAWin() {
        GameController testTTT = new TicTacToeController(new TicTacToeModel(3, 3), new MostSquaresHeuristic());
        testTTT.setHumanPlays();
        testTTT.playMove(0); // human
        testTTT.playMove(1); // comp
        testTTT.playMove(4); // human
        testTTT.playMove(7); // comp
        testTTT.playMove(8); // human
        assertTrue(testTTT.getBoard().isAWin(GameController.HUMAN));
        assertFalse(testTTT.getBoard().isAWin(GameController.COMPUTER));
    }

    @Test
    void testPositionValue() {
        GameController testTTT = new TicTacToeController(new TicTacToeModel(3, 3), new MostSquaresHeuristic());
        testTTT.setHumanPlays();
        testTTT.playMove(0); // human
        testTTT.playMove(1); // comp
        testTTT.playMove(4); // human
        testTTT.playMove(7); // comp
        testTTT.playMove(8); // human
        assertTrue(testTTT.getBoard().positionValue() == GameController.HUMAN_WIN);
    }
}
