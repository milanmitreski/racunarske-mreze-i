package materials.v08.p01_intro;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPIntro {
    private static final int BUFF_SIZE = 1024;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress host = InetAddress.getByName("www.example.com");
            int port = 12345;

            String message = "Hello World";
            byte[] messageBytes = message.getBytes();
            DatagramPacket packetToSend = new DatagramPacket(
                    messageBytes, messageBytes.length, host, port
            );
            socket.send(packetToSend);

            byte[] receivePacketBytes = new byte[BUFF_SIZE];
            DatagramPacket packetToReceive = new DatagramPacket(receivePacketBytes, BUFF_SIZE);
            socket.receive(packetToReceive);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
