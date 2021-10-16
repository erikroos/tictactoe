package tictactoe;

import java.util.*;

/**
 * Code (c) Hanzehogeschool Groningen
 *
 * Textual UI (View) for TicTacToeGame
 */
class View
{
    private Scanner reader = new Scanner(System.in);
    private Controller gameController;
 
    public View()
    {
        do {
            System.out.println("*** New game ***\n");
            gameController = new Controller();
            if (gameController.computerPlays()) {
                System.out.println("I start:\n");
            } else {
                System.out.println("You start:\n");
            }
            while (!gameController.gameOver())
            {
                gameController.playMove(move());
                System.out.println(gameController);
            }
            System.out.println("Game over: " + gameController.winner() + " wins");
        } while (nextGame());
    }
    
    public static void main(String[] args) {
        new View();
    }
    
    private int move()
    {
        if (gameController.computerPlays())
        {
            int compMove = gameController.chooseMove(true);
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
        Character yn;
        do {
            System.out.print("Another game? Enter y/n: ");
            yn = (reader.next()).charAt(0);
            System.out.println("" + yn);
        } while (!(yn=='Y' || yn == 'y' || yn == 'N' || yn == 'n'));
        return yn == 'Y' || yn == 'y';
    }
}


