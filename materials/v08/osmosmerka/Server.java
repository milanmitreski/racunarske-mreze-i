package v08.osmosmerka;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final static int PORT = 5555;
    private final static String FILE_PATH = "materials/v08/osmosmerka/osmosmerka.txt";

    public static void main(String[] args) {
        Osmosmerka osmosmerka;

        try {
            osmosmerka = new Osmosmerka(FILE_PATH);
        } catch (FileNotFoundException e) {
            System.err.println("Unable to load. Exiting");
            return;
        }

        try (ServerSocket server = new ServerSocket(PORT)) {
            Socket client;
            while(true) {
                client = server.accept();
                System.err.println("Client accepted. Dispatching...");
                new Thread(new ServerWorker(client, osmosmerka)).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
