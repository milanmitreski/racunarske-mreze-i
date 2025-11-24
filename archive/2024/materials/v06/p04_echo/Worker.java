package materials.v06.p04_echo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Worker implements Runnable {
    private Socket client;

    public Worker(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        serve(client);
        try {
            if(client != null && !client.isClosed())
                client.close();
        } catch (IOException e) {
            System.err.println("Communication error!");
        }
        System.out.println("Client handler " + Thread.currentThread().getId() + " finished!");
    }

    private void serve(Socket client) {
        try (
                PrintStream out = new PrintStream(
                        new BufferedOutputStream(
                            client.getOutputStream()
                        ),
                        true
                );
                Scanner in = new Scanner(
                        new BufferedInputStream(
                                client.getInputStream()
                        )
                )
        ) {
            String line;
            while(in.hasNext()) {
                // Primamo zahtev od klijenta
                line = in.nextLine();

                // Vracamo odgovor
                out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Client handler " + Thread.currentThread().getId() + " errored!");
            e.printStackTrace();
        }
    }
}
