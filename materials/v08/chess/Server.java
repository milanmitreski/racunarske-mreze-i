package v08.chess;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {
    private static final int PORT = 5050;
    static final Map<Integer, ChessPlayer> TABLE = Collections.synchronizedMap(new HashMap<>());
    static final AtomicInteger ID_COUNTER = new AtomicInteger();

    public static void main(String[] args) {
        try(ServerSocket server = new ServerSocket(PORT)) {
            Socket client;
            while(true) {
                client = server.accept();
                System.err.println("Client accepted. Dispatching...");
                new Thread(new ServerWorker(client)).start();
            }
        } catch (IOException e) {
            System.err.println("Startup error");
        }
    }
}
