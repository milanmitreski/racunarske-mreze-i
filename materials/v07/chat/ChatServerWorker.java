package v07.chat;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ChatServerWorker extends Thread {

    private Socket client;
    private String username;
    private PrintStream printer;

    public ChatServerWorker(Socket client) {
        this.client = client;
        try {
            this.printer = new PrintStream(
                    new BufferedOutputStream(
                            client.getOutputStream()
                    ),
                    true
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(String message) {
        printer.println(message);
    }

    public String getUsername() {
        return username;
    }

    private void serve() {
        try(
                Scanner scanner = new Scanner(
                        new BufferedInputStream(
                                client.getInputStream()
                        )
                )
        ) {
            this.username = scanner.nextLine();
            sendMessage("Connected users: " + ChatServer.getUsernames());
            ChatServer.broadcast(this, username + " connected.");
            while(scanner.hasNextLine()) {
                String message = scanner.nextLine();
                ChatServer.broadcast(this, username + ": " + message);
                if(message.equalsIgnoreCase("bye"))
                    break;
            }
            ChatServer.broadcast(this, username + " disconnected.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ChatServer.remove(this);
    }

    @Override
    public void run() {
        serve();
        if(client != null && !client.isClosed()) {
            try {
                client.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if(printer != null) {
            printer.close();
        }
    }
}
