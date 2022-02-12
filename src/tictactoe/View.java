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
    private GameController gameController;

    public static void main(String[] args) {
        View v = new View();
        v.start();
    }
 
    public View() {
        reader = new Scanner(System.in);
    }

    private void start() {
        System.out.println("Press 1 for Tic Tac Toe or 2 for Othello: ");
        switch(reader.next().charAt(0)) {
            case '1':
                gameController = new TicTacToeController(new Model()); // TODO use factory
                break;
            case '2':
            default:
                gameController = new OthelloController(); // TODO use factory
        }


        System.out.println("Press 1 for AI or 2 for Heuristics: ");
        boolean useAI = (reader.next()).charAt(0) == '1';

        System.out.println("Press 1 for local or 2 for server game: ");
        char mode = (reader.next()).charAt(0);

        // Server mode
        if (mode == '2') {
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
            System.out.println("*** New game of " + gameController.NAME + " ***\n");
            gameController.init();
            if (gameController.computerPlays()) {
                System.out.println("I start:\n");
            } else {
                System.out.println("You start:\n");
            }
            gameController.printBoard();

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
                System.out.print("Your move please. X = ");
                int x = reader.nextInt();
                System.out.print("Y = ");
                int y = reader.nextInt();
                humanMove = gameController.coordsToNUmber(x, y);
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