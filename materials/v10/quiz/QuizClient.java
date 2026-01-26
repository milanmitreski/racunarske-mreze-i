package v10.quiz;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class QuizClient {

    private static final String SERVER_HOST = "localhost";
    private static final Integer SERVER_PORT = 9000;
    private static final Integer BUFF_SIZE = 2048;

    public static void main(String[] args) {
        try(
                DatagramSocket socket = new DatagramSocket();
                Scanner in = new Scanner(System.in)
        ) {
            byte[] inputBuff = new byte[BUFF_SIZE], outputBuff;
            DatagramPacket input, output;
            String answer;

            outputBuff = new byte[1];
            output = new DatagramPacket(
                    outputBuff,
                    outputBuff.length,
                    InetAddress.getByName(SERVER_HOST),
                    SERVER_PORT
            );
            socket.send(output);

            while(true) {
                input = new DatagramPacket(inputBuff, BUFF_SIZE);
                socket.receive(input);

                String received = new String(
                        input.getData(),
                        input.getOffset(),
                        input.getLength(),
                        StandardCharsets.UTF_8
                );
                System.out.println(received);
                if(received.equals("Tacno") || received.equals("Netacno"))
                    break;

                answer = in.nextLine();
                outputBuff = answer.getBytes(StandardCharsets.UTF_8);
                output = new DatagramPacket(
                        outputBuff,
                        outputBuff.length,
                        InetAddress.getByName(SERVER_HOST),
                        SERVER_PORT
                );
                socket.send(output);
            }
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
