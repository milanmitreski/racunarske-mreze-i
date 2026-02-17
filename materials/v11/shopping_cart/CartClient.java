package v11.shopping_cart;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class CartClient {

    public static void main(String[] args) {

        try (DatagramSocket datagramSocket = new DatagramSocket();
            Scanner sc = new Scanner(System.in)) {

            String name = "";

            while(true) {
                System.out.println("Enter your name: ");
                name = sc.nextLine().trim();
                DatagramPacket reqName = new DatagramPacket(name.getBytes(), name.length(), InetAddress.getLocalHost(), CartServer.PORT);
                datagramSocket.send(reqName);

                byte[] buff = new byte[CartServer.BUFF_SIZE];
                DatagramPacket respFirst = new DatagramPacket(buff, buff.length);
                datagramSocket.receive(respFirst);
                String firstResponse = new String(respFirst.getData(), 0, respFirst.getLength());
                System.out.println(firstResponse.trim());
                if (!firstResponse.startsWith("Vec"))
                    break;
            }

            while (true) {
                String command = name + " " + sc.nextLine().trim();
                DatagramPacket req =  new DatagramPacket(command.getBytes(),command.length(),InetAddress.getLocalHost(),CartServer.PORT);
                datagramSocket.send(req);

                byte [] buffResp = new byte[CartServer.BUFF_SIZE];
                DatagramPacket resp = new DatagramPacket(buffResp,buffResp.length);
                datagramSocket.receive(resp);
                String respString = new String(resp.getData(),0,resp.getLength());
                System.out.println(respString.trim());

                if (respString.startsWith("Total:"))
                    break;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
