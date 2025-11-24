package materials.v07.p02_chat;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class ChatClientSender implements Runnable {

    private PrintStream printStream;
    private String username;

    public ChatClientSender(OutputStream outputStream, String username) {
        this.printStream = new PrintStream(
                new BufferedOutputStream(
                        outputStream
                ),
                true
        );
        this.username = username;
    }

    private void serve() {
        printStream.println(username);
        try(Scanner scanner = new Scanner(System.in)) {
            String message;
            do {
                message = scanner.nextLine();
                printStream.println(message);
            } while (!message.equalsIgnoreCase("bye"));
        }
    }

    @Override
    public void run() {
        serve();
        if(printStream != null) {
            printStream.close();
        }
    }
}
