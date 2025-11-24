package materials.v06.p01_intro;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketIntro {

    // Port na kom kreiramo soket pomocu kojeg server osluskuje zahteve
    public static final int PORT = 9000;

    public static void main(String[] args) {
        // socket() + bind() ( + listen())
        try (ServerSocket server = new ServerSocket(PORT)) {
            // server.bind(new InetSocketAddress(PORT));

            while(true) {
                // Cekamo na zahtev od klijenta
                Socket client = server.accept();
                Thread worker = new Thread(new Worker(client));
                worker.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class Worker implements Runnable {

        private final Socket client;

        public Worker(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            serve(client);
            if(client != null && !client.isClosed()) {
                try {
                    client.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        private void serve(Socket client) {
            // serve
        }
    }
}
