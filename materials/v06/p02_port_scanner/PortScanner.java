package materials.v06.p02_port_scanner;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

public class PortScanner {

    public static void main(String[] args) {
        String host = "alas.matf.bg.ac.rs";
        for(int port = 1; port <= 65535; port++) {
            System.out.println("Testing port: " + port);
            try(Socket socket = new Socket(host, port)) {
                System.out.println("Socket data: " + socket);
                System.out.println("Found: " + new Date());
            } catch (UnknownHostException e) {
                System.err.println("Invalid host!");
            } catch (IOException e) {
                System.out.println("No one is here...");
            }
        }
    }
}
