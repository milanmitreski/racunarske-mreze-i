package v09.datetime;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class DateTimeClient {

    private static final int BUFF_SIZE = 1024;
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 5050;

    public static void main(String[] args) {
        try(
                DatagramSocket socket = new DatagramSocket();
                Scanner scanner = new Scanner(System.in);
        ) {
            System.out.println("What information do you need? (DATE/TIME/DATETIME)");
            String option = scanner.nextLine();
            if(!option.equals("DATE") && !option.equals("TIME") && !option.equals("DATETIME")) {
                System.err.println("Wrong option");
                return;
            }

            byte[] optionBytes = option.getBytes(StandardCharsets.US_ASCII);
            DatagramPacket packetToSend = new DatagramPacket(
                    optionBytes, optionBytes.length, InetAddress.getByName(SERVER_HOST), SERVER_PORT
            );
            socket.send(packetToSend);

            DatagramPacket packetToReceive = new DatagramPacket(new byte[BUFF_SIZE], BUFF_SIZE);
            socket.receive(packetToReceive);

            String response = new String(
                    packetToReceive.getData(), 0, packetToReceive.getLength(), StandardCharsets.US_ASCII
            );
            System.out.println(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
