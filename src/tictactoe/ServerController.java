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

        String username = "ITV2Dtutor"; // TODO: get from config
        boolean useAI = true; // TODO: get from config

        if (!login(username)) {
            System.out.println("Login failed - terminating...");
            return;
        }

        // TODO: enable other flow, where we don't subscribe but wait for challenges
        if (!subscribe()) {
            System.out.println("Subscribe failed - terminating...");
            return;
        }

        // Variables we need in the game loop
        Controller gameController = new Controller();
        String response = null;
        boolean inGame = false;
        boolean gameEnded = false;
        int move;
        boolean optionToQuit = false; // TODO make settable

        // Result counters
        int[] results = new int[3];
        for (int i = 0; i < 3; i++) {
            results[i] = 0;
        }

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
                    String mover = getParameterValueFromServerInfo(info, "PLAYERTOMOVE");
                    if (mover.equals(username)) {
                        // We start (from the controller's point of view that's the computer)
                        gameController.setComputerPlays();
                    } else {
                        // Opponent starts (from the controller's point of view that's the human)
                        gameController.setHumanPlays();
                    }
                }
                // TODO check for challenge
            } else {
                // Already in a game
                if (responseParts.length > 3 && responseParts[2].equals("YOURTURN")) {
                    // We must make a move
                    move = gameController.chooseMove(useAI);
                    out.println("move " + move);
                    System.out.println("We made move: " + move);
                    gameController.playMove(move); // TODO we could integrate this in the next if block
                    gameController.printBoard();
                } else if (responseParts.length > 3 && responseParts[2].equals("MOVE")) {
                    // Either we or opponent made a move
                    System.out.println("Move was made: " + info);
                    String mover = getParameterValueFromServerInfo(info, "PLAYER");
                    if (!mover.equals(username)) {
                        // Opponent's move, so register
                        String moveString = getParameterValueFromServerInfo(info, "MOVE");
                        move = Integer.parseInt(moveString);
                        System.out.println("Opponent made move: " + move);
                        gameController.playMove(move);
                        gameController.printBoard();
                    }
                } else if (responseParts.length > 3 && responseParts[2].equals("WIN")) {
                    System.out.println("We won! " + info);
                    gameEnded = true;
                    results[0]++;
                } else if (responseParts.length > 3 && responseParts[2].equals("LOSS")) {
                    System.out.println("We lost... " + info);
                    gameEnded = true;
                    results[1]++;
                } else if (responseParts.length > 3 && responseParts[2].equals("DRAW")) {
                    System.out.println("It's a draw... " + info);
                    gameEnded = true;
                    results[2]++;
                }
            }

            if (gameEnded) {
                System.out.println("Overall results (win/loss/draw): " + results[0] + "/" + results[1] + "/" + results[2]);
                if (optionToQuit) {
                    Scanner reader = new Scanner(System.in);
                    System.out.print("Another server game coming up! Enter y to continue or n to stop now: ");
                    char yn = (reader.next()).charAt(0);
                    if ((yn == 'n' || yn == 'N')) {
                        logout(username);
                        return;
                    }
                }
                // Game on for the next round!
                inGame = false;
                gameEnded = false;
                if (!subscribe()) {
                    System.out.println("Subscribe failed - terminating...");
                    return;
                }
            }
        }
    }

    // Helper methods from here:

    private boolean login(String username) {
        out.println("login " + username);
        String response = null;
        try {
            response = in.readLine();
        } catch (IOException e) {
            return false;
        }
        if (!response.equals("OK")) {
            return false;
        } else {
            System.out.println("Login successful");
        }
        return true;
    }

    private void logout(String username) {
        out.println("logout");
    }

    private boolean subscribe() {
        out.println("subscribe tic-tac-toe"); // does not give back OK
        String response = null;
        try {
            response = in.readLine();
        } catch (IOException e) {
            return false;
        }
        System.out.println("Subscribed to tic-tac-toe");
        if (response != null) {
            System.out.println("Server gave response: " + response);
        }
        return true;
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

    private String getParameterValueFromServerInfo(String info, String param) {
        String value = null;
        Pattern pattern = Pattern.compile(param + ": \"([^\"]+)\"");
        Matcher matcher = pattern.matcher(info);
        if (matcher.find()) {
            value = matcher.group(1);
        }
        return value;
    }
}
