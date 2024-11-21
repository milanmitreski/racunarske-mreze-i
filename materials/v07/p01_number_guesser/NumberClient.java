package materials.v07.p01_number_guesser;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class NumberClient {

    public static final String HOST = "localhost";
    public static final int PORT = NumberServer.DEFAULT_PORT;

    public static void main(String[] args) {
        try(
                Socket socket = new Socket(HOST, PORT);
                Scanner serverScanner = new Scanner(
                        new BufferedInputStream(
                                socket.getInputStream()
                        )
                );
                PrintStream serverPrinter = new PrintStream(
                        new BufferedOutputStream(
                                socket.getOutputStream()
                        ),
                        true
                );
                Scanner localScanner = new Scanner(System.in)
        ) {
            while(serverScanner.hasNextLine()) {
                String message = serverScanner.nextLine();
                System.out.println("server : " + message);
                if(message.equals("Cestitam! Pogodili ste broj!")) {
                    break;
                }
                System.out.print("client : ");
                int guess = localScanner.nextInt();
                serverPrinter.println(guess);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
