package konsultacije1608.jul_25_zadatak2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final String WORDS_FILE_NAME = "materials/konsultacije1608/jul_25_zadatak2/words.txt";
    private static final int PORT = 5555;

    static void main() {
        Hangman.loadWords(WORDS_FILE_NAME);

        try(ServerSocket server = new ServerSocket(PORT)) {
            while(true) {
                Socket s = server.accept();
                new Thread(new ClientWorker(s)).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
