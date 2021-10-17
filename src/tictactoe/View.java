package tictactoe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

/**
 * Code (c) Hanzehogeschool Groningen
 *
 * Textual UI (View) for TicTacToeGame
 */
class View
{
    private Scanner reader;
    private Controller gameController;
 
    public View() {
        reader = new Scanner(System.in);
        gameController = new Controller();
    }

    private void start() {
        System.out.println("Press 1 for Server mode or 2 for local game: ");
        char mode = (reader.next()).charAt(0);
        // Server mode
        if (mode == '1') {
            try {
                startServer();
            } catch (IOException e) {
                System.out.println("Connecting to server failed - terminating...");
            }
            return;
        }
        // Local mode
        do {
            System.out.println("*** New game ***\n");
            if (gameController.computerPlays()) {
                System.out.println("I start:\n");
            } else {
                System.out.println("You start:\n");
            }
            while (!gameController.gameOver())
            {
                gameController.playMove(move());
                System.out.println(gameController.board);
            }
            System.out.println("Game over: " + gameController.winner() + " wins");
        } while (nextGame());
    }

    private void startServer() throws IOException {
        String hostName = "145.33.225.170";
        int portNumber = 7789;
        String name = "erikroos";

        Socket connection = new Socket(hostName, portNumber);
        PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response;

        in.readLine(); // copyright line
        in.readLine(); // OK
        out.println("login " + name);
        response = in.readLine();
        if (!response.equals("OK")) {
            System.out.println("Login failed - terminating...");
            return;
        }
        out.println("get gamelist");
        response = in.readLine();
        if (response.equals("OK")) {
            System.out.println("Games: " + in.readLine());
        }
        out.println("get playerlist");
        response = in.readLine();
        if (response.equals("OK")) {
            System.out.println("Players: " + in.readLine());
        }
        out.println("logout");
    }
    
    public static void main(String[] args) {
        View v = new View();
        v.start();
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
        char yn;
        do {
            System.out.print("Another game? Enter y/n: ");
            yn = (reader.next()).charAt(0);
            System.out.println("" + yn);
        } while (!(yn=='Y' || yn == 'y' || yn == 'N' || yn == 'n'));
        return yn == 'Y' || yn == 'y';
    }
}