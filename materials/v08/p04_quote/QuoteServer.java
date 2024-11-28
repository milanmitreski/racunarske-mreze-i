package materials.v08.p04_quote;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class QuoteServer {
    private static final int PORT = 1984;
    private static final int BUFF_SIZE = 1024;
    private static final String QUOTES_FILE = "src/udp/quote/one_liners.txt";

    public static void main(String[] args) {
        try (
                DatagramSocket socket = new DatagramSocket(PORT);
                Scanner in = new Scanner(new FileInputStream(QUOTES_FILE))
        ) {
            byte[] requestBytes = new byte[BUFF_SIZE];
            byte[] responseBytes;
            while(true) {
                DatagramPacket requestPacket = new DatagramPacket(requestBytes, requestBytes.length);
                socket.receive(requestPacket);

                String response = getQuote(in);
                responseBytes = response.getBytes(StandardCharsets.UTF_8);
                DatagramPacket responsePacket = new DatagramPacket(
                        responseBytes, responseBytes.length,
                        requestPacket.getAddress(), requestPacket.getPort()
                );
                socket.send(responsePacket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getQuote(Scanner in) {
        if(in.hasNextLine())
            return in.nextLine();
        else
            return "Now is not a good time to have a quote.";
    }
}
