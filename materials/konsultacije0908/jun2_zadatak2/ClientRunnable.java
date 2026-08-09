package konsultacije0908.jun2_zadatak2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

public class ClientRunnable implements Runnable {

    private Spojnice game;
    private Socket client;

    public ClientRunnable(Socket client, Map<String, String> pairs) {
        this.client = client;
        this.game = new Spojnice(pairs);
    }

    @Override
    public void run() {
        try (
            Scanner in = new Scanner(
                new BufferedInputStream(
                        this.client.getInputStream()
                )
            );
            PrintStream out = new PrintStream(
                    new BufferedOutputStream(
                            this.client.getOutputStream()
                    ),
                    true
            )
        ){
            int i = 0;

            while(i < 7) {
                out.println(game.getLeftColumnString());
                out.println(game.getRightColumnString());
                out.println(game.getLeftPair(i));

                String clientInput = in.nextLine(); // MATCHED A
                String[] parsed = clientInput.split(" ");

                if(parsed.length != 2
                        || !parsed[0].equals("MATCH")
                        || parsed[1].length() != 1
                        || !Character.isUpperCase(parsed[1].charAt(0))){
                    break;
                }

                if(this.game.guess(i, parsed[1].charAt(0)))
                    out.println("TACNO");
                else
                    out.println("NETACNO");
                i++;
            }

            if(i != 7) {
                out.println("ERROR!");
            }

            out.println("POGODJENO: " + this.game.getGuessedPairs() + "/7");
        } catch (IOException e) {
            System.out.println("COMMUNICATION ERROR");
        }
    }
}
