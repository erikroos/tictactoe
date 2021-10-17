package tictactoe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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

    protected void serverLoop() throws IOException {
        // TODO: get from config
        String name = "erikroos";

        out.println("login " + name);

        String response = in.readLine();
        if (!response.equals("OK")) {
            System.out.println("Login failed - terminating...");
            return;
        } else {
            System.out.println("Login succesful");
        }

        out.println("get gamelist");
        response = in.readLine();
        if (response.equals("OK")) {
            System.out.println("Games: " + in.readLine());
        } else {
            System.out.println("Server response was " + response + " instead of OK.");
        }

        out.println("get playerlist");
        response = in.readLine();
        if (response.equals("OK")) {
            System.out.println("Players: " + in.readLine());
        } else {
            System.out.println("Server response was " + response + " instead of OK.");
        }

        // TODO: add while-true loop to handle server flow (possibly later: using a thread)

        out.println("logout");
    }
}
