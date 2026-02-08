package Asocijacije;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;

public class AsocijacijeClient {

    private static Set<String> solvedColumns = new TreeSet<>();
    private static Set<String> openFields = new TreeSet<>();

    public static void main(String[] args) {

        try (DatagramSocket datagramSocket = new DatagramSocket();
            Scanner sc = new Scanner(System.in)) {

            while (true) {
                String command = sc.nextLine().trim();

                if (command.equals("IGIVEUP"))
                    break;

                String [] parts = command.split(" ");
                boolean success = true;
                switch(parts[0]) {
                    case "OPEN":
                        if (parts.length != 2) {
                            System.out.println("Pogresna komanda");
                            success = false;
                            break;
                        }
                        if (openFields.contains(parts[1])) {
                            System.out.println("Otvoreno polje!");
                            success = false;
                            break;
                        }

                        if (!parts[1].matches("[ABCD][1234]")) {
                            System.out.println("Pogresno formatirano polje");
                            success = false;
                            break;
                        }

                        break;
                    case "SOLVE":
                        if (parts.length != 3) {
                            System.out.println("Pogresna komanda");
                            success = false;
                            break;
                        }

                        if (solvedColumns.contains(parts[1])) {
                            System.out.println("Resena kolona");
                            success = false;
                            break;
                        }

                        if (!parts[1].matches("[ABCD]")) {
                            System.out.println("Nepostojana kolona");
                            success = false;
                            break;
                        }

                        break;
                    case "FINAL":
                        if (parts.length!=2) {
                            System.out.println("Pogresna komanda");
                            success = false;
                            break;
                        }
                        break;
                }

                if (success) {
                    DatagramPacket req = new DatagramPacket(command.getBytes(), command.length(), InetAddress.getLocalHost(),AsocijacijeServer.PORT);
                    datagramSocket.send(req);

                    byte [] buff = new byte[AsocijacijeServer.BUFF_SIZE];
                    DatagramPacket resp = new DatagramPacket(buff,buff.length);
                    datagramSocket.receive(resp);
                    String respString = new String(resp.getData(),0,resp.getLength()).trim();
                    System.out.println(respString);

                    if (parts[0].equals("OPEN")) {
                        openFields.add(parts[1]);
                    }

                    if (parts[0].equals("SOLVE") && respString.equals("Tacno")) {
                        solvedColumns.add(parts[1]);
                        for (int i=1;i<=4;i++)
                            openFields.add(parts[1]+i);
                    }

                    if (parts[0].equals("FINAL") && respString.equals("Tacno"))
                        break;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
