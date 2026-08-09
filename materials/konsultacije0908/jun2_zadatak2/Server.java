package konsultacije0908.jun2_zadatak2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private static int PORT = 5555;
    private static Map<String, String> pairs;

    public static void main() {
        initPairs();

        try(ServerSocket server = new ServerSocket(PORT)) {
            while(true) {
                Socket client = server.accept();
                new Thread(new ClientRunnable(client, pairs)).start();
            }
        } catch (IOException e) {
            System.out.println("INTERNAL SERVER ERROR");
        }
    }

    private static void initPairs() {
        pairs = new HashMap<>();

        pairs.put("TCP", "Socket");
        pairs.put("UDP", "DatagramPacket");
        pairs.put("HTTP", "web");
        pairs.put("DNS", "domen");
        pairs.put("IP", "adresa");
        pairs.put("port", "proces");
        pairs.put("thread", "nit");
    }
}
