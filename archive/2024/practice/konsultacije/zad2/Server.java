package konsultacije.zad2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {

    public static final int PORT = 1996;
    public static final int DFAULT_ELO = 1300;

    public static List<Sahista> sahisti = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ObradaKlijenta obradaKlijenta = new ObradaKlijenta(clientSocket, sahisti);
                Thread t = new Thread(obradaKlijenta);
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
