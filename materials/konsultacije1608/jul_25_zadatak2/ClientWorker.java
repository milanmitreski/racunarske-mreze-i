package konsultacije1608.jul_25_zadatak2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientWorker implements Runnable {
    private Socket client;
    private Hangman game;

    public ClientWorker(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try(
            Scanner in = new Scanner(
                    new BufferedInputStream(
                            client.getInputStream()
                    )
            );
            PrintStream out = new PrintStream(
                    new BufferedOutputStream(
                            client.getOutputStream()
                    ),
                    true
            )
        ) {
            while(game == null && in.hasNextLine()) {
                String setup = in.nextLine();
                String[] parts = setup.split(" ");

                if(parts.length != 2) {
                    out.println("Invalid number of arguments");
                    continue;
                }

                int lives;
                try {
                    lives = Integer.parseInt(parts[1]);
                } catch (NumberFormatException e) {
                    out.println("Invalid argument: number of lives");
                    continue;
                }

                String name = parts[0];

                game = new Hangman(name, lives);
                out.println(game.currentState());
            }

            if(game == null)
                return;

            while(!game.isGameOver() && in.hasNextLine()) {
                String letter = in.nextLine().trim();
                System.out.println(letter);
                if(!letter.matches("[A-Z]")) {
                    out.println("Invalid command - the command should contain an uppercase letter only.");
                }
                out.println(game.guess(letter.charAt(0)));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
