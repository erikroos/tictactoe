package view;

import helper.Helper;
import controller.*;
import model.Model;
import model.OthelloModel;
import model.TicTacToeModel;

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
        int move;
        if (gameController.computerPlays())
        {
            move = gameController.chooseMove();
            if (move == -1) {
                System.out.println("Computer has to pass.");
            } else {
                int[] coords = Helper.moveToCoords(move, gameController.getBoard().horizontalSize);
                System.out.println("Computer move. X = " + coords[0] + ", Y = " + coords[1]);
            }
        } else {
            do {
                System.out.println("Your move please (-1, -1 to pass).");
                System.out.print("X = ");
                int x = reader.nextInt();
                System.out.print("Y = ");
                int y = reader.nextInt();
                if (x == -1 || y == -1) {
                    move = -1;
                } else {
                    move = Helper.coordsToMove(x, y, gameController.getBoard().horizontalSize);
                }
            } while (!gameController.getBoard().moveOk(move, GameController.HUMAN));
        }
        return move;
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
