package tictactoe;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Tests {

    @Test
    void testChooseMove() {
        GameController testTTT = new TicTacToeController();
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
        GameController testTTT = new TicTacToeController();
        testTTT.setHumanPlays();
        testTTT.playMove(0); // human
        testTTT.playMove(1); // comp
        testTTT.playMove(4); // human
        testTTT.playMove(7); // comp
        testTTT.playMove(8); // human
        assertTrue(testTTT.getBoard().isAWin(TicTacToeController.HUMAN));
        assertFalse(testTTT.getBoard().isAWin(TicTacToeController.COMPUTER));
    }

    @Test
    void testPositionValue() {
        GameController testTTT = new TicTacToeController();
        testTTT.setHumanPlays();
        testTTT.playMove(0); // human
        testTTT.playMove(1); // comp
        testTTT.playMove(4); // human
        testTTT.playMove(7); // comp
        testTTT.playMove(8); // human
        assertTrue(testTTT.getBoard().positionValue() == TicTacToeController.HUMAN_WIN);
    }
}