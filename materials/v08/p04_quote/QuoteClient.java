package materials.v08.p04_quote;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class QuoteClient {

    private static final int SERVER_PORT = 1984;
    private static final String SERVER_HOST = "localhost";
    private static final int BUFF_SIZE = 1024;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            // saljemo "prazan" request
            DatagramPacket requestPacket = new DatagramPacket(
                    new byte[1], 1, InetAddress.getByName(SERVER_HOST), SERVER_PORT
            );
            socket.send(requestPacket);

            DatagramPacket responsePacket = new DatagramPacket(new byte[BUFF_SIZE], BUFF_SIZE);
            socket.receive(responsePacket);
            String response = new String(
                    responsePacket.getData(), 0, responsePacket.getLength(), StandardCharsets.UTF_8
            );
            System.out.println("Quote of the moment: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
