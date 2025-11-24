package materials.v06.p04_echo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static final int PORT = 20000;
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Started server on port " + PORT);
            while(true) {
                System.out.println("Listening for clients...");
                Socket client = server.accept();

                System.out.println("Client accepted! Dispatching thread...");
                new Thread(new Worker(client)).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
