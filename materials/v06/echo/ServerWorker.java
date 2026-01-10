package v06.echo;

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
        serveClient();
        if(client != null && !client.isClosed()) {
            try {
                client.close();
            } catch (IOException e) {
                System.err.println("Greska u komunikaciji");
            }
        }
    }

    private void serveClient() {
        try(
                PrintStream out = new PrintStream(
                    new BufferedOutputStream(
                            this.client.getOutputStream()
                    ),
                    true
                );
                Scanner in = new Scanner(
                        this.client.getInputStream()
                );
        ) {
            while(in.hasNextLine()) {
                out.println(in.nextLine()); // send(recv())
            }
        } catch (IOException e) {
            System.err.println("Greska u komunikaciji");
        }
    }

}
