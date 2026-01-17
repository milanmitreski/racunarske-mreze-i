package v08.chess;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    private final static String SERVER_HOST = "localhost";
    private final static int SERVER_PORT = 5050;

    public static void main(String[] args) {
        try(
                Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
                Scanner serverIn = new Scanner(
                        new BufferedInputStream(
                                socket.getInputStream()
                        )
                );
                PrintStream serverOut = new PrintStream(
                        new BufferedOutputStream(
                                socket.getOutputStream()
                        ),
                        true
                );
                Scanner sysIn = new Scanner(System.in);
        ) {
            String line;
            while(true) {
                line = sysIn.nextLine();
                serverOut.println(line);
                if(line.equals("bye"))
                    break;

                System.out.println(serverIn.nextLine());
            }
        } catch (UnknownHostException e) {
            System.err.println("Specified hostname unknown.");
        } catch (IOException e) {
            System.err.println("Communication error");
        }
    }
}
