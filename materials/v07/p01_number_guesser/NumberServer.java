package materials.v07.p01_number_guesser;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NumberServer {

    public static final int DEFAULT_PORT = 12321;

    public static void main(String[] args) {
        try(
                ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT)
        ) {
            Socket client;
            while(true) {
                client = serverSocket.accept();
                System.out.println("Client accepted");
                new Thread(new NumberWorker(client)).start();
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
