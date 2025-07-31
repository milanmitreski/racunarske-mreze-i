package konsultacije.zad3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Objects;

public class UDPServer {

    public static final int PORT = 1212;

    public static void main(String[] args) {
        try (DatagramSocket serverSocket = new DatagramSocket(PORT)) {
            while (true) {
                byte [] buf = new byte [256];
                DatagramPacket request = new DatagramPacket(buf, buf.length);
                serverSocket.receive(request);

                byte[] data = request.getData();

                int n = ((data[0] & 0xFF) << 24) |
                        ((data[1] & 0xFF) << 16) |
                        ((data[2] & 0xFF) << 8) |
                        (data[3] & 0xFF);

                String s = new String(data,4, request.getLength()-4);
                System.out.println(s);
                System.out.println(n);

                int hashed = request.getPort();

                for (int i=0; i<n; i++) {
                    hashed = Objects.hash(s,hashed);
                }

                if (hashed == -1) {
                    hashed =0;
                }

                byte [] responseBuffer = new byte[4];

                responseBuffer[0] = (byte) ((hashed >>24) & 0xFF);
                responseBuffer[1] = (byte) ((hashed >>16) & 0xFF);
                responseBuffer[2] = (byte) ((hashed >>8) & 0xFF);
                responseBuffer[3] = (byte) (hashed & 0xFF);

                DatagramPacket response = new DatagramPacket(responseBuffer, responseBuffer.length, request.getAddress(), request.getPort());
                serverSocket.send(response);

            }
        } catch (IOException e) {
            System.out.println("Greska");
        }
    }

}
