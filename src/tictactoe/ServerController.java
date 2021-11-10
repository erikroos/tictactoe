package tictactoe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerController {
    Socket connection;
    PrintWriter out;
    BufferedReader in;

    public ServerController() throws IOException {
        // TODO: get from config
        String hostName = "145.33.225.170";
        int portNumber = 7789;

        connection = new Socket(hostName, portNumber);
        out = new PrintWriter(connection.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        System.out.println(in.readLine()); // title line
        System.out.println(in.readLine()); // copyright line
    }

    /**
     * The loop that controls automatic gameplay on the server.
     * Currently supports one game of Tic-Tac-Toe.
     */
    protected void serverLoop() {
        if (connection == null) {
            System.out.println("No connection - terminating...");
            return;
        }

        String username = "lyricalgangster"; // TODO: get from config
        boolean useAI = true; // TODO: get from config

        try {
            login(username);
        } catch (IOException e) {
            System.out.println("Login failed - terminating...");
            return;
        }

        // TODO: enable other flow, where we don't subscribe but wait for challenges
        subscribe();

        Controller gameController = new Controller();
        String response = null;
        boolean inGame = false;
        boolean gameEnded = false;
        int move;
        Pattern pattern;
        Matcher matcher;
        while (true) {
            try {
                response = in.readLine();
            } catch (IOException e) {
                // Something fishy happened, ignore
            }
            if (response == null) {
                // Nothing yet, sleep 0.2 sec and continue
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    // Sleep interrupted, ignore
                }
                continue;
            }
            // We got a response!
            String[] responseParts = response.split(" ");
            String info = "";
            if (response.contains("{")) {
                info = response.substring(response.indexOf("{"));
            }
            if (!inGame) {
                // Game not started yet
                if (responseParts.length > 3 && responseParts[2].equals("MATCH")) {
                    // We've been matched!
                    System.out.println("Matched! " + info);
                    inGame = true;
                    gameController.init();
                    // Set who begins
                    pattern = Pattern.compile("PLAYERTOMOVE: \"([^\"]+)\"");
                    matcher = pattern.matcher(info);
                    if (matcher.find()) {
                        String mover = matcher.group(1);
                        if (mover.equals(username)) {
                            // We start (from the controller's point of view that's the computer)
                            gameController.setComputerPlays();
                        } else {
                            // Opponent starts (from the controller's point of view that's the human)
                            gameController.setHumanPlays();
                        }
                    }
                }
            } else {
                // Already in a game
                if (responseParts.length > 3 && responseParts[2].equals("YOURTURN")) {
                    // We must make a move
                    move = gameController.chooseMove(useAI);
                    gameController.playMove(move);
                    out.println("move " + move);
                    System.out.println("We made move: " + move);
                    gameController.printBoard();
                } else if (responseParts.length > 3 && responseParts[2].equals("MOVE")) {
                    // Either we or opponent made a move
                    System.out.println("Move was made: " + info);
                    pattern = Pattern.compile("PLAYER: \"([^\"]+)\"");
                    matcher = pattern.matcher(info);
                    if (matcher.find()) {
                        String mover = matcher.group(1);
                        if (!mover.equals(username)) {
                            // Opponent's move, so register
                            pattern = Pattern.compile("MOVE: \"(\\d)\"");
                            matcher = pattern.matcher(info);
                            if (matcher.find()) {
                                String moveString = matcher.group(1);
                                move = Integer.parseInt(moveString);
                                gameController.playMove(move);
                                System.out.println("Opponent made move: " + move);
                                gameController.printBoard();
                            }
                        }
                    }
                } else if (responseParts.length > 3 && responseParts[2].equals("WIN")) {
                    System.out.println("We won! " + info);
                    gameEnded = true;
                } else if (responseParts.length > 3 && responseParts[2].equals("LOSS")) {
                    System.out.println("We lost... " + info);
                    gameEnded = true;
                } else if (responseParts.length > 3 && responseParts[2].equals("DRAW")) {
                    System.out.println("It's a draw... " + info);
                    gameEnded = true;
                }
            }

            if (gameEnded) {
                Scanner reader = new Scanner(System.in);
                System.out.print("Another server game? Enter y/n: ");
                char yn = (reader.next()).charAt(0);
                if (!(yn == 'y' || yn == 'Y')) {
                    break;
                }
                // Game on for the next round!
                inGame = false;
                gameEnded = false;
                subscribe();
            }
        }

        logout(username);
    }

    // Helper methods from here:

    private void login(String username) throws IOException {
        out.println("login " + username);
        String response = in.readLine();
        if (!response.equals("OK")) {
            System.out.println("Login failed - terminating...");
            return;
        } else {
            System.out.println("Login successful");
        }
    }

    private void logout(String username) {
        out.println("logout");
    }

    private void subscribe() {
        out.println("subscribe tic-tac-toe"); // does not give back OK
        System.out.println("Subscribed to tic-tac-toe");
    }

    private void getGameList() throws IOException {
        out.println("get gamelist");
        String response = in.readLine();
        if (response.equals("OK")) {
            System.out.println("Games: " + in.readLine());
        } else {
            System.out.println("Server response was " + response + " instead of OK.");
        }
    }

    private void getPlayerList() throws IOException {
        out.println("get playerlist");
        String response = in.readLine();
        if (response.equals("OK")) {
            System.out.println("Players: " + in.readLine());
        } else {
            System.out.println("Server response was " + response + " instead of OK.");
        }
    }
}
