package tictactoe;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TicTacToeTest {

    @Test
    void testChooseMove() {
        TicTacToe testTTT = new TicTacToe();
        testTTT.setHumanPlays();
        testTTT.playMove(0); // human
        testTTT.playMove(1); // comp
        testTTT.playMove(4); // human
        testTTT.playMove(7); // comp
        testTTT.playMove(6); // human
        testTTT.playMove(3); // comp
        TicTacToe.Best nextMove = testTTT.chooseMove(TicTacToe.HUMAN);
        assertTrue(nextMove.row == 0 && nextMove.column == 2);
    }

    @Test
    void testIsAWin() {
        TicTacToe testTTT = new TicTacToe();
        testTTT.setHumanPlays();
        testTTT.playMove(0); // human
        testTTT.playMove(1); // comp
        testTTT.playMove(4); // human
        testTTT.playMove(7); // comp
        testTTT.playMove(8); // human
        assertTrue(testTTT.isAWin(TicTacToe.HUMAN));
        assertFalse(testTTT.isAWin(TicTacToe.COMPUTER));
    }

    @Test
    void testPositionValue() {
        TicTacToe testTTT = new TicTacToe();
        testTTT.setHumanPlays();
        testTTT.playMove(0); // human
        testTTT.playMove(1); // comp
        testTTT.playMove(4); // human
        testTTT.playMove(7); // comp
        testTTT.playMove(8); // human
        assertTrue(testTTT.positionValue() == TicTacToe.HUMAN_WIN);
    }
}