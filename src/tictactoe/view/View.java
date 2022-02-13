package tictactoe.view;

import tictactoe.controller.*;
import tictactoe.model.Model;
import tictactoe.model.OthelloModel;
import tictactoe.model.TicTacToeModel;

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
        boolean asking = true;
        while (asking) {
            System.out.println("Press 1 for Tic Tac Toe or 2 for Othello: ");
            switch (reader.next().charAt(0)) {
                case '1':
                    try {
                        gameController = ControllerFactory.getController("tictactoe");
                        asking = false;
                    } catch (ClassNotFoundException e) {
                        System.out.println("Unkown option: " + e.getMessage());
                    }
                    break;
                case '2':
                    try {
                        gameController = ControllerFactory.getController("othello");
                        asking = false;
                    } catch (ClassNotFoundException e) {
                        System.out.println("Unkown option: " + e.getMessage());
                    }
                    break;
                default:
                    System.out.println("Unkown option...");
            }
        }

        System.out.println("Press 1 for local or 2 for server game: ");
        char mode = (reader.next()).charAt(0);

        // Server mode
        if (mode == '2') {
            // Delegate control to server controller
            try {
                ServerController sc = new ServerController(gameController, "ITV2Dtutor"); // TODO get username from config
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
                int square = move(); // get move from either human or computer
                gameController.playMove(square);
                gameController.printBoard();
            }
            System.out.println("Game over: " + gameController.winner() + " wins");
        } while (nextGame());
    }
    
    private int move()
    {
        if (gameController.computerPlays())
        {
            int compMove = gameController.chooseMove();
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