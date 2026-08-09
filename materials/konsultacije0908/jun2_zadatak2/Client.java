package konsultacije0908.jun2_zadatak2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    private static String SERVER_HOST = "localhost";
    private static int SERVER_PORT = 5555;

    static void main() {
        try(
            Socket s = new Socket(SERVER_HOST, SERVER_PORT);
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
            Scanner stdIn = new Scanner(
                    System.in
            )
        ) {
            while(true) {
                String left = in.nextLine();
                if(left.startsWith("POGODJENO:")) {
                    System.out.println(left);
                    break;
                }

                String right = in.nextLine();
                String leftPair = in.nextLine();

                System.out.println(left.replace(";", "\n"));
                System.out.println(right.replace(";", "\n"));
                System.out.println("NEXT PAIR: " + leftPair);

                System.out.print("YOUR GUESS: ");
                String guess = stdIn.nextLine();
                out.println(guess);

                String answer = in.nextLine();
                System.out.println(answer);
                if(answer.equals("ERROR!"))
                    break;
            }
        } catch (UnknownHostException e) {
            System.out.println("UNKNOWN HOST");
        } catch (IOException e) {
            System.out.println("COMMUNICATION ERROR");
        }
    }
}
