package materials.v08.p03_echo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class EchoClient {

    private static final int BUFF_SIZE = 1024;
    private static final int SERVER_PORT = 5000;
    private static final String SERVER_HOST = "localhost";

    public static void main(String[] args) {
        try (
                DatagramSocket socket = new DatagramSocket();
                Scanner scanner = new Scanner(System.in)
        ) {
            byte[] messageBytes;
            byte[] buff = new byte[BUFF_SIZE];
            while(true) {
                System.out.print("Enter line: ");
                String message = scanner.nextLine();
                if(message.equalsIgnoreCase("EXIT"))
                    break;

                messageBytes = message.getBytes(StandardCharsets.UTF_8);
                DatagramPacket requestPacket = new DatagramPacket(
                        messageBytes, messageBytes.length, InetAddress.getByName(SERVER_HOST), SERVER_PORT
                );
                socket.send(requestPacket);

                DatagramPacket responsePacket = new DatagramPacket(
                        buff, buff.length
                );
                socket.receive(responsePacket);

                String response = new String(
                        responsePacket.getData(), 0, responsePacket.getLength(), StandardCharsets.UTF_8
                );
                System.out.println("ECHO: " + response);
            }

            System.out.println("Client has finished.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
