package materials.v07.p02_chat;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    private static final String HOST = "localhost";
    private static final int PORT = ChatServer.PORT;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        try(
                Socket socket = new Socket(HOST, PORT);
        ){

            Thread senderThread = new Thread(new ChatClientSender(socket.getOutputStream(), username));
            Thread listenerThread = new Thread(new ChatClientListener(socket.getInputStream(), username));

            senderThread.start();
            listenerThread.start();

            senderThread.join();
            listenerThread.join();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
