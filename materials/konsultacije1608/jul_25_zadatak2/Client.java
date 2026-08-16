package konsultacije1608.jul_25_zadatak2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    private static final int SERVER_PORT = 5555;
    private static final String SERVER_NAME = "localhost";

    static void main() {
        try(
            Socket s = new Socket(SERVER_NAME, SERVER_PORT);
            Scanner in = new Scanner(
                    new BufferedInputStream(
                            s.getInputStream()
                    )
            );
            PrintStream out = new PrintStream(
                    new BufferedOutputStream(
                            s.getOutputStream()
                    ),
                    true
            );
            Scanner sysIn = new Scanner(System.in)
        ) {
            while(sysIn.hasNextLine()) {
                out.println(sysIn.nextLine());
                String answer = in.nextLine();
                System.out.println(answer);
                if(answer.startsWith("GAME OVER!"))
                    break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
