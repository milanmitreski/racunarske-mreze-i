package v10.restaurant;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class RestaurantClient {

    private static final String SERVER_HOST = "localhost";
    private static final Integer SERVER_PORT = 8500;
    private static final Integer BUFF_SIZE = 2048;

    public static void main(String[] args) {
        try(DatagramSocket socket = new DatagramSocket();
            Scanner in = new Scanner(System.in)
        ) {
            String request = in.nextLine();
            byte[] requestBytes = request.getBytes(StandardCharsets.UTF_8);
            DatagramPacket output = new DatagramPacket(
                    requestBytes,
                    requestBytes.length,
                    InetAddress.getByName(SERVER_HOST),
                    SERVER_PORT
            );
            socket.send(output);

            byte[] responseBytes = new byte[BUFF_SIZE];
            DatagramPacket input = new DatagramPacket(responseBytes, BUFF_SIZE);
            socket.receive(input);
            String response = new String (
                    input.getData(),
                    input.getOffset(),
                    input.getLength(),
                    StandardCharsets.UTF_8
            );
            System.out.println(response);
        } catch (SocketException e) {
            System.err.println("Unable to create Socket");
            return;
        } catch (UnknownHostException e) {
            System.err.println("Unable to identify host");
            return;
        } catch (IOException e) {
            System.err.println("Network error.");
            return;
        }
    }
}
