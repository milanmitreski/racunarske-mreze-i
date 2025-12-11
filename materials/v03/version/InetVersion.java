package v03.version;

import java.net.*;

public class InetVersion {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress addr1 = InetAddress.getByName("google.com");
        System.out.println(addr1);
        System.out.println(getVersion(addr1));
        printAddress(addr1.getAddress());
    }

    private static int getVersion(InetAddress addr) {
        int size = addr.getAddress().length;
        switch (size) {
            case 4: return 4;
            case 16: return 6;
            default: return -1;
        }

        // 2. nacin - ne praktikovati kastovanje ili ispitivanje da li je addr instanca klase Inet4 ili Inet6
        /* if(addr instanceof Inet4Address) {
            return 4;
        } else if(addr instanceof Inet6Address) {
            return 6;
        } else if (addr instanceof InetSocketAddress) {
            // Sta sa ovima
        } else {
            return -1;
        } */
    }

    private static void printAddress(byte[] address) {
        // Ispisuje na standardni izlaz bajtove IPv4 adrese
        System.out.print("Bajtovi IP adrese su: ");
        for(byte b : address) {
            int unsignedByte = b < 0 ? b + 256 : b;
            System.out.print(unsignedByte + " ");
        }
        System.out.println();
    }
}
