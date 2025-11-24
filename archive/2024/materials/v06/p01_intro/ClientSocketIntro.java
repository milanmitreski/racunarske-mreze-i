package materials.v06.p01_intro;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocketIntro {

    public static void main(String[] args) {
        try (
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
                );
        ) {
            System.out.println(socket);
            System.out.println(socket.getPort());
            System.out.println(socket.getInetAddress());
            System.out.println(socket.getLocalPort());
            System.out.println(socket.getLocalAddress());
            // Treba poslati zahtev
            out.write("GET / HTTP/1.1\r\n" +
                    "Host: www.matf.bg.ac.rs\r\n" +
                    "Accept: text/html\r\n" +
                    "Connection: keep-alive\r\n" +
                    "\r\n");
            out.flush();

            // Nakon poslatog zahteva, citamo odgovor
            String line;
            while((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
