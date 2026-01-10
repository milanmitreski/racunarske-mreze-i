package v06.intro;

import java.io.IOException;
import java.net.Socket;

public class ServerWorker implements Runnable {
    private final Socket client;

    public ServerWorker(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        serveClient();
        if(client != null && !client.isClosed()) {
            try {
                client.close();
            } catch (IOException e) {
                System.err.println("Greska u komunikaciji");
            }
        }
    }

    private void serveClient() {
        // TODO(Serve)
    }

}
