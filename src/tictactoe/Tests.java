package tictactoe;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Tests {

    @Test
    void testChooseMove() {
        Controller testTTT = new Controller();
        testTTT.setHumanPlays();
        testTTT.playMove(0); // human
        testTTT.playMove(1); // comp
        testTTT.playMove(4); // human
        testTTT.playMove(7); // comp
        testTTT.playMove(6); // human
        testTTT.playMove(3); // comp
        BestMove nextMove = testTTT.chooseMoveAI(Controller.HUMAN);
        assertTrue(nextMove.row == 0 && nextMove.column == 2);
    }

    @Test
    void testIsAWin() {
        Controller testTTT = new Controller();
        testTTT.setHumanPlays();
        testTTT.playMove(0); // human
        testTTT.playMove(1); // comp
        testTTT.playMove(4); // human
        testTTT.playMove(7); // comp
        testTTT.playMove(8); // human
        assertTrue(testTTT.board.isAWin(Controller.HUMAN));
        assertFalse(testTTT.board.isAWin(Controller.COMPUTER));
    }

    @Test
    void testPositionValue() {
        Controller testTTT = new Controller();
        testTTT.setHumanPlays();
        testTTT.playMove(0); // human
        testTTT.playMove(1); // comp
        testTTT.playMove(4); // human
        testTTT.playMove(7); // comp
        testTTT.playMove(8); // human
        assertTrue(testTTT.board.positionValue() == Controller.HUMAN_WIN);
    }
}