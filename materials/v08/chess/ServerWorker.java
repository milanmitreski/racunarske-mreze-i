package v08.chess;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ServerWorker implements Runnable {
    private final Socket client;

    public ServerWorker(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        serve();
        if(client != null && !client.isClosed()) {
            try {
                client.close();
            } catch (IOException e) {
                System.err.println("[" + client + "]: Connection error");
            }
        }
    }

    private void serve() {
        try (
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
            String line;
            String[] commands;
            while(clientIn.hasNextLine()) {
                line = clientIn.nextLine();
                commands = line.split(" ");

                int id = -1, elo = -1;
                String name;

                switch (commands[0]) {
                    case "select" -> {
                        if (commands.length != 2) {
                            clientOut.println("Wrong number of arguments for select command");
                            continue;
                        }

                        try {
                            id = Integer.parseInt(commands[1]);
                        } catch (NumberFormatException e) {
                            clientOut.println("Wrong id (number) format.");
                            continue;
                        }

                        if (id < 0 || !Server.TABLE.containsKey(id)) {
                            clientOut.println("Player with the given ID not present in the table.");
                            continue;
                        }

                        clientOut.println(Server.TABLE.get(id).toString());
                    }
                    case "update" -> {
                        if (commands.length != 3) {
                            clientOut.println("Wrong number of arguments for update command");
                            continue;
                        }

                        try {
                            id = Integer.parseInt(commands[1]);
                            elo = Integer.parseInt(commands[2]);
                        } catch (NumberFormatException e) {
                            clientOut.println("Wrong id or elo (number) format.");
                            continue;
                        }

                        if (id < 0 || !Server.TABLE.containsKey(id)) {
                            clientOut.println("Player with the given ID not present in the table.");
                            continue;
                        }

                        if (elo < 1300) {
                            clientOut.println("Elo out of specified range");
                            continue;
                        }

                        synchronized (Server.TABLE) {
                            Server.TABLE.get(id).setElo(elo);
                        }

                        clientOut.println("ChessPlayer information updated.");
                    }
                    case "insert" -> {
                        if (commands.length < 2) {
                            clientOut.println("Wrong number of arguments for insert command");
                            continue;
                        }

                        name = line.split(" ", 2)[1];
                        id = Server.ID_COUNTER.incrementAndGet();

                        synchronized (Server.TABLE) {
                            Server.TABLE.put(id, new ChessPlayer(name, 1300));
                        }

                        clientOut.println("ChessPlayer inserted.");
                    }
                    default -> clientOut.println("Invalid command.");
                }
            }
            System.err.println("Client serving finished.");
        } catch (IOException e) {
            System.err.println("[" + client + "]: Connection error");
        }
    }
}
