package materials.v06.p04_echo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class EchoClient {
    public static void main(String[] args) {
        String host = "localhost";
        try(
                Socket socket = new Socket(host, EchoServer.PORT);
                PrintStream out = new PrintStream(
                        new BufferedOutputStream(
                                socket.getOutputStream()
                        ),
                        true
                );
                Scanner in = new Scanner(
                        new BufferedInputStream(
                                socket.getInputStream()
                        )
                );
                Scanner stdIn = new Scanner(System.in);
        ) {
            System.out.println("Connected to the echo server @ " + host);

            while(true) {
                String line = stdIn.nextLine();
                if(line.trim().equalsIgnoreCase("exit"))
                    break;

                // Saljemo serveru
                out.println(line);

                // Primamo odgovor od servera
                System.out.println(in.nextLine());
            }
        } catch (UnknownHostException e) {
            System.err.println("Unknown host!");
        } catch (IOException e) {
            System.err.println("Communication error!");
        }

        System.out.println("Disconnected from the echo server @ " + host);
        System.out.println("Shutting down...");
    }
}
