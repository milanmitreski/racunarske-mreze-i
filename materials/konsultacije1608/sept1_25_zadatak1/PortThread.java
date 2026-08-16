package konsultacije1608.sept1_25_zadatak1;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

public class PortThread extends Thread {
    private int port;
    private String protocol;
    private List<InetAddress> addressesToCheck;
    private int valid;


    public PortThread(int port, String protocol, List<InetAddress> addressesToCheck) {
        this.port = port;
        this.protocol = protocol;
        this.addressesToCheck = addressesToCheck;
        this.valid = 0;
    }

    @Override
    public void run() {
        for(InetAddress address : this.addressesToCheck) {
            System.out.println("ADDRESS: " + address + " PROTOCOL: " + protocol);
            try(Socket s = new Socket(address, this.port)) {
                System.err.println("AVAILABLE --- PROTOCOL: " + protocol + "\t\tPORT: " + port + "\t\tHOST: " + address.getHostAddress());
                valid++;
            } catch (IOException e) {
                System.err.println("UNAVAILABLE --- PROTOCOL: " + protocol + "\t\tPORT: " + port + "\t\tHOST: " + address.getHostAddress());
            }
        }
    }

    public String getReport() {
        return protocol + "(" + port + "): " + valid + " active.";
    }
}
