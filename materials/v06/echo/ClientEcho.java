package v06.echo;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientEcho {
    public static void main(String[] args) {
        try (
                // socket() i connect()
                Socket socket = new Socket("localhost", ServerEcho.PORT);
                PrintStream out = new PrintStream(
                        new BufferedOutputStream(
                                socket.getOutputStream()
                        ),
                        true
                );
                Scanner in = new Scanner(
                        socket.getInputStream()
                );
                Scanner sin = new Scanner(
                        System.in
                )
        ) {
            while (sin.hasNextLine()) {
                String line = sin.nextLine();
                if (line.equalsIgnoreCase("exit")) {
                    break;
                }

                out.println(line); // send()
                System.out.println(in.nextLine()); // recv()
            }
        } catch (UnknownHostException e) {
            System.err.println("Nepoznat host!");
        } catch (IOException e) {
            System.err.println("Greska pri komunikaciji");
        }
    }
}
