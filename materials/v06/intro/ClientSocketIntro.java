package v06.intro;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocketIntro {
    // HTTP/HTTPS koristi TCP
    // Mozemo poslati jedan HTTP zahtev prekoTCP
    // Poslacemo zahtev ka www.matf.bg.ac.rs
    public static void main(String[] args) {
        try(
                // socket() i connect()
                Socket socket = new Socket("www.matf.bg.ac.rs", 80);
                BufferedWriter out = new BufferedWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream()
                        )
                );
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()
                        )
                )
        ) {
            System.out.println(socket);
            // Target/Server
            System.out.println(socket.getPort());
            System.out.println(socket.getInetAddress());
            // Host
            System.out.println(socket.getLocalPort());
            System.out.println(socket.getLocalAddress());

            // Request - send()
            out.write("GET / HTTP/1.1\r\n" +
                    "Host: www.matf.bg.ac.rs\r\n" +
                    "Accept: text/html\r\n" +
                    "Connection: keep-alive\r\n" +
                    "\r\n"
            );
            out.flush();

            // Response - recv()
            String line;
            while((line = in.readLine()) != null) {
                System.out.println(line);
            }

        } catch (UnknownHostException e) {
            System.err.println("Nepoznat host!");
        } catch (IOException e) {
            System.err.println("Greska pri komunikaciji");
        }
    }
}
