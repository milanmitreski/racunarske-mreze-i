package materials.v06.p03_datetime;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class DateTimeClient {
    public static void main(String[] args) {
        // Koristimo ovaj host za "privid" komunikacije racunara samog sa sobom
        String host = "localhost";
        try (
                Socket socket = new Socket(host, DateTimeServer.PORT);
                // OutputStream (byte) - Writer (char/String) - PrintStream (int, char, String..)
                //                       write()                println(), print(), printf()
                PrintStream printStream = new PrintStream(
                        new BufferedOutputStream(
                                socket.getOutputStream()
                        ),
                        true // "obavezan" flag, da ne bi morali da zovemo flush() svaki put
                );
                // InputStream (byte) - Reader (char/String) - Scanner (int, char, String)
                //                      read()                 next(), nextLine(), nextInt()
                //                                             hasNext(), hasNextInt()
                Scanner sc = new Scanner(
                        new BufferedInputStream(
                                socket.getInputStream()
                        )
                )
        ) {
            System.out.println("What information do you need? (DATE/TIME/DATETIME)");
            Scanner stdIn = new Scanner(System.in);
            String option = stdIn.next();
            if(!option.equals("DATE") && !option.equals("TIME") && !option.equals("DATETIME")) {
                System.err.println("Wrong option!");
                return;
            }
            // Saljemo serveru opciju
            printStream.println(option);
            // Cekamo od servera odgovor
            System.out.println(sc.next());
        } catch (UnknownHostException e) {
            System.err.println("Unknown host!");
        } catch (IOException e) {
            System.err.println("Communication error!");
        }

    }
}
