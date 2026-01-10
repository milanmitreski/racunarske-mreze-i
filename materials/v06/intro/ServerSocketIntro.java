package v06.intro;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketIntro {
    public static int PORT = 9000;

    public static void main(String[] args) {
        try(ServerSocket server = new ServerSocket(PORT)) { // socket(), bind() i listen()
            // server.bind(new InetSocketAddress(PORT))

            while(true) {
                Socket client = server.accept(); // We use this Socket to communicate with client
                Thread worker = new Thread(new ServerWorker(client));
                worker.start();
            }
        } catch (IOException e) {
            System.err.println("Greska u komunikaciji");
        }
    }
}
