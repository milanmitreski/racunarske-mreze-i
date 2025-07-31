package konsultacije.zad3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class UDPClient {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            String s = sc.nextLine();
            int n = sc.nextInt();

//            String s1 = s + "|" + n;
//            byte [] data = s1.getBytes();

            byte [] sBytes = s.getBytes();
            byte [] data = new byte[4 + sBytes.length];

            data[0] = (byte) ((n>>24) & 0xFF);
            data[1] = (byte) ((n>>16) & 0xFF);
            data[2] = (byte) ((n>>8) & 0xFF);
            data[3] = (byte) (n & 0xFF);

            for (int i=0;i<sBytes.length; i++) {
                data[4+i] = sBytes[i];
            }

            try (DatagramSocket socket = new DatagramSocket()) {
                socket.setSoTimeout(5000);
                InetAddress address = InetAddress.getByName("localhost");
                int serverPort = UDPServer.PORT;

                DatagramPacket packet = new DatagramPacket(data, data.length, address, serverPort);

                boolean success = false;
                while (!success) {
                    try {
                        socket.send(packet);
                        System.out.println("Poslat paket");
                        byte[] buf = new byte[256];
                        DatagramPacket response = new DatagramPacket(buf, buf.length);
                        socket.receive(response);
                        System.out.println("primljen response");
                        byte [] responseData = response.getData();

                        int hash = ((responseData[0] & 0xFF) << 24) |
                                ((responseData[1] & 0xFF) << 16) |
                                ((responseData[2] & 0xFF) << 8) |
                                (responseData[3] & 0xFF);

                        if (hash == -1 ) {
                            System.out.println("Greska");
                        }

                        System.out.println("hash je: " + hash);

                        success = true;
                    } catch (SocketTimeoutException e) {

                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
