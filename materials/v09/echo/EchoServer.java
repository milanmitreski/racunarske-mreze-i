package v09.echo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class EchoServer {

    private static final int PORT = 5050;
    private static final int BUFF_SIZE = 1024;

    public static void main(String[] args) {
        try(DatagramSocket socket = new DatagramSocket(PORT)) {
            byte[] responseBytes;
            byte[] buff = new byte[BUFF_SIZE];
            while(true) {
                DatagramPacket requestPacket = new DatagramPacket(buff, buff.length);
                socket.receive(requestPacket);
                String request = new String(
                        requestPacket.getData(),
                        0,
                        requestPacket.getLength(),
                        StandardCharsets.UTF_8
                );

                String response = request;
                responseBytes = response.getBytes(StandardCharsets.UTF_8);
                DatagramPacket responsePacket = new DatagramPacket(
                        responseBytes,
                        responseBytes.length,
                        requestPacket.getAddress(),
                        requestPacket.getPort()
                );
                socket.send(responsePacket);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
