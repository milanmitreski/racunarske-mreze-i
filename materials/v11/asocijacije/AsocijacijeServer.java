package v11.asocijacije;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class AsocijacijeServer {

    public final static int PORT = 12345;
    public final static int BUFF_SIZE = 1024;

    private static Map<Integer,List<String>> igra = new TreeMap<>();

    public static void main(String[] args) {
        loadQuestions();

        try (DatagramSocket datagramSocket = new DatagramSocket(PORT)) {
            while (true) {
                byte[] buff = new byte[BUFF_SIZE];
                DatagramPacket req = new DatagramPacket(buff, buff.length);
                datagramSocket.receive(req);
                String reqString = new String(req.getData(), 0, req.getLength());

                String[] parts = reqString.split(" ");
                String responseString = "";
                switch (parts[0]) {
                    case "OPEN":
                        int column = parts[1].charAt(0) - 'A';
                        int field = parts[1].charAt(1) - '1';
                        responseString = igra.get(column).get(field);
                        break;
                    case "SOLVE":
                        int columnSolve = parts[1].charAt(0) - 'A';
                        if (igra.get(columnSolve).getLast().equals(parts[2]))
                            responseString = "Tacno";
                        else
                            responseString = "Netacno";
                        break;
                    case "FINAL":
                        if (igra.get(4).getLast().equals(parts[1]))
                            responseString = "Tacno";
                        else
                            responseString = "Netacno";
                        break;
                    default:
                        responseString = "Greska";
                        break;
                }

                DatagramPacket resp = new DatagramPacket(responseString.getBytes(), responseString.length(), req.getAddress(), req.getPort());
                datagramSocket.send(resp);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void loadQuestions() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("asocijacije.txt")))) {
            int index =0;
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null)
                    break;
                igra.put(index, List.of(line.split(" ")));
                index++;
            }
        } catch (IOException e ) {
            e.printStackTrace();
        }
    }
}
