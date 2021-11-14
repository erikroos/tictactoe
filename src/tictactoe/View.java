package tictactoe;

import java.io.IOException;
import java.util.*;

/**
 * Code (c) Hanzehogeschool Groningen
 *
 * Textual UI (View) for TicTacToeGame
 */
class View
{
    private Scanner reader;
    private TicTacToeController gameController;
 
    public View() {
        reader = new Scanner(System.in);
        gameController = new TicTacToeController();
    }

    private void start() {
        System.out.println("Press 1 for AI or 2 for Heuristics: ");
        boolean useAI = (reader.next()).charAt(0) == '1';

        System.out.println("Press 1 for Server mode or 2 for local game: ");
        char mode = (reader.next()).charAt(0);

        // Server mode
        if (mode == '1') {
            // Delegate control to server controller
            try {
                ServerController sc = new ServerController(gameController, useAI, "ITV2Dtutor"); // TODO get username from config
                sc.serverLoop();
            } catch (IOException e) {
                System.out.println("Server setup failed - terminating...");
            }
            return;
        }

        // Local mode
        do {
            System.out.println("*** New game ***\n");
            gameController.init();
            if (gameController.computerPlays()) {
                System.out.println("I start:\n");
            } else {
                System.out.println("You start:\n");
            }
            // The central Game Loop
            while (!gameController.gameOver())
            {
                int square = move(useAI); // get move from either human or computer
                gameController.playMove(square);
                gameController.printBoard();
            }
            System.out.println("Game over: " + gameController.winner() + " wins");
        } while (nextGame());
    }
    
    public static void main(String[] args) {
        View v = new View();
        v.start();
    }
    
    private int move(boolean useAI)
    {
        if (gameController.computerPlays())
        {
            int compMove = gameController.chooseMove(useAI);
            System.out.println("Computer move = " + compMove);
            return compMove;
        } else {
            int humanMove;
            do {
                System.out.print("Human move (enter number for position: 012-345-678) = ");
                humanMove = reader.nextInt();
            } while (!gameController.moveOk(humanMove));
            return humanMove;
        }
    }
    
    private boolean nextGame()
    {
        char yn;
        do {
            System.out.print("Another game? Enter y/n: ");
            yn = (reader.next()).charAt(0);
            System.out.println("" + yn);
        } while (!(yn=='Y' || yn == 'y' || yn == 'N' || yn == 'n'));
        return yn == 'Y' || yn == 'y';
    }
}