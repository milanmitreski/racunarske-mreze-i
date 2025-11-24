package materials.v08.p02_datetime;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class DateTimeServer {

    private static final int PORT = 12345;
    private static final int BUFF_SIZE = 1024;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            while(true) {
                DatagramPacket requestPacket = new DatagramPacket(new byte[BUFF_SIZE], BUFF_SIZE);
                socket.receive(requestPacket);

                String option = new String(
                        requestPacket.getData(), 0, requestPacket.getLength(), StandardCharsets.US_ASCII
                );
                LocalDateTime now = LocalDateTime.now();
                String response = switch (option) {
                    case "DATE" -> now.toLocalDate().toString();
                    case "TIME" -> now.toLocalTime().toString();
                    case "DATETIME" -> now.toString();
                    default -> "ERROR!";
                };

                byte[] responseBytes = response.getBytes();
                DatagramPacket responsePacket = new DatagramPacket(
                        responseBytes, responseBytes.length, requestPacket.getAddress(), requestPacket.getPort()
                );
                socket.send(responsePacket);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
