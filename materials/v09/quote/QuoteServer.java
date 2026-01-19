package v09.quote;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class QuoteServer {

    private static final int PORT = 5050;
    private static final int BUFF_SIZE = 1024;

    private static final String QUOTES_FILE = "materials/v09/quote/one_liners.txt";
    private static final List<String> QUOTES = new ArrayList<>();

    public static void main(String[] args) {
        loadQuotes();
        try(DatagramSocket socket = new DatagramSocket(PORT)) {
            byte[] responseBytes;
            byte[] buff = new byte[BUFF_SIZE];
            Random r = new Random();

            while(true) {
                DatagramPacket requestPacket = new DatagramPacket(buff, buff.length);
                socket.receive(requestPacket);

                String response = QUOTES.get(r.nextInt(QUOTES.size()));
                responseBytes = response.getBytes(StandardCharsets.UTF_8);
                DatagramPacket responsePacket = new DatagramPacket(
                        responseBytes, responseBytes.length,
                        requestPacket.getAddress(), requestPacket.getPort()
                );
                socket.send(responsePacket);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadQuotes() {
        try (Scanner file = new Scanner(
                new BufferedInputStream(
                        new FileInputStream(
                                QUOTES_FILE
                        )
                )
        )) {
            while(file.hasNextLine()) {
                QUOTES.add(file.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found. No quotes inserted.");
        }
    }
}
