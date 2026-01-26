package v10.quiz;

import java.net.InetAddress;
import java.util.Objects;

public class Client {
    private InetAddress clientAddress;
    private Integer clientPort;

    public Client(InetAddress clientAddress, Integer clientPort) {
        this.clientAddress = clientAddress;
        this.clientPort = clientPort;
    }

    public InetAddress getClientAddress() {
        return clientAddress;
    }

    public Integer getClientPort() {
        return clientPort;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(clientAddress, client.clientAddress) && Objects.equals(clientPort, client.clientPort);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientAddress, clientPort);
    }
}
