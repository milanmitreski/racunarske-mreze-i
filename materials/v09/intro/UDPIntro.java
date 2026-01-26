package v09.intro;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class UDPIntro {

    private static final int BUFF_SIZE = 1024;

    public static void main(String[] args) {
        try(DatagramSocket socket = new DatagramSocket()) {
            InetAddress host = InetAddress.getByName("www.example.com");
            int port = 12345;

            String message = "Hello World!";
            byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);

            DatagramPacket packetToSend = new DatagramPacket(
                    messageBytes, messageBytes.length, host, port
            );
            socket.send(packetToSend);

            byte[] buff = new byte[BUFF_SIZE];
            DatagramPacket packetToReceive = new DatagramPacket(buff, BUFF_SIZE);
            socket.receive(packetToReceive);

            String response = new String(
                    packetToReceive.getData(),
                    packetToReceive.getOffset(),
                    packetToReceive.getLength(),
                    StandardCharsets.UTF_8
            );
            System.out.println(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
