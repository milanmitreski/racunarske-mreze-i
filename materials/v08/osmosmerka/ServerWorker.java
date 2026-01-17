package v08.osmosmerka;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ServerWorker implements Runnable {

    private final Socket client;
    private final Osmosmerka osmosmerka;
    private final List<String> leftToSolve;

    public ServerWorker(Socket client, Osmosmerka osmosmerka) {
        this.client = client;
        this.osmosmerka = osmosmerka;
        this.leftToSolve = new ArrayList<>(this.osmosmerka.getWords());
    }

    @Override
    public void run() {
        serve();
        if(client != null && !client.isClosed()) {
            try {
                client.close();
            } catch (IOException e) {
                System.err.println("[" + client + "]: Failed to close connection");
            }
        }
    }

    private void serve() {
        try(
                Scanner clientIn = new Scanner(
                        new BufferedInputStream(
                                client.getInputStream()
                        )
                );
                PrintStream clientOut = new PrintStream(
                        new BufferedOutputStream(
                                client.getOutputStream()
                        ),
                        true
                )
        ) {
            clientOut.println(osmosmerka);

            String line;
            String[] commands;
            while(clientIn.hasNextLine()) {
                line = clientIn.nextLine();

                if(line.equals("IGIVEUP")) {
                    break;
                }

                commands = line.split(" ");

                if(commands.length != 4) {
                    clientOut.println("Invalid number of command arguments");
                    continue;
                }

                if(!leftToSolve.contains(commands[3])) {
                    clientOut.println("You have entered a word you don't need to guess");
                    continue;
                }

                int i = -1, j = -1;
                try {
                    i = Integer.parseInt(commands[1]);
                    j = Integer.parseInt(commands[2]);
                } catch (NumberFormatException e) {
                    clientOut.println("Starting field coordinates are not numbers");
                    continue;
                }

                if(i < 0 || i > 7 || j < 0 || j > 7) {
                    clientOut.println("Starting field coordinates are out of bounds");
                    continue;
                }

                boolean output = false;
                switch (commands[0]) {
                    case "N":
                        output = osmosmerka.N(i, j, commands[3]);
                        break;
                    case "E":
                        output = osmosmerka.E(i, j, commands[3]);
                        break;
                    case "W":
                        output = osmosmerka.W(i, j, commands[3]);
                        break;
                    case "S":
                        output = osmosmerka.S(i, j, commands[3]);
                        break;
                    case "NE":
                        output = osmosmerka.NE(i, j, commands[3]);
                        break;
                    case "NW":
                        output = osmosmerka.NW(i, j, commands[3]);
                        break;
                    case "SE":
                        output = osmosmerka.SE(i, j, commands[3]);
                        break;
                    case "SW":
                        output = osmosmerka.SW(i, j, commands[3]);
                        break;
                    default:
                        clientOut.println("Direction command invalid.");
                        continue;
                }

                if(output) {
                    leftToSolve.remove(commands[3]);
                    if(leftToSolve.isEmpty()) {
                        clientOut.println("WELLDONE");
                        break;
                    } else {
                        clientOut.println("You guessed the word " + commands[3] + ". Remaining words: " + leftToSolve);
                    }
                } else {
                    clientOut.println("Unable to match word. Try again;");
                }
            }
        } catch (IOException e) {
            System.err.println("[" + client + "]: Connection error");
        }
    }
}
