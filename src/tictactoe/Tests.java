package tictactoe;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Tests {

    @Test
    void testChooseMove() {
        GameController testTTT = new TicTacToeController(new Model());
        testTTT.init();
        testTTT.setHumanPlays();
        testTTT.playMove(0); // human
        testTTT.playMove(1); // comp
        testTTT.playMove(4); // human
        testTTT.playMove(8); // comp
        testTTT.playMove(6); // human
        testTTT.playMove(3); // comp
        assertTrue(testTTT.chooseMove(true) == 2);
    }

    @Test
    void testIsAWin() {
        GameController testTTT = new TicTacToeController(new Model());
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
        GameController testTTT = new TicTacToeController(new Model());
        testTTT.setHumanPlays();
        testTTT.playMove(0); // human
        testTTT.playMove(1); // comp
        testTTT.playMove(4); // human
        testTTT.playMove(7); // comp
        testTTT.playMove(8); // human
        assertTrue(testTTT.getBoard().positionValue() == GameController.HUMAN_WIN);
    }
}