package v08.osmosmerka;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 5555;

    public static void main(String[] args) {
        try (
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
                Scanner sysIn = new Scanner(System.in)
        ) {
            String osmosmerka = serverIn.nextLine();
            for(int i = 0; i < 8; i++) {
                System.out.println(osmosmerka.substring(8*i, 8*(i+1)));
            }
            System.out.println(osmosmerka.substring(64));

            String line;
            while(true) {
                line = sysIn.nextLine();
                serverOut.println(line);
                if(line.equals("IGIVEUP"))
                    break;

                line = serverIn.nextLine();
                System.out.println(line);
                if(line.equals("WELLDONE"))
                    break;
            }

        } catch (UnknownHostException e) {
            System.err.println("Specified hostname unknown.");
        } catch (IOException e) {
            System.err.println("Communication error");
        }
    }
}