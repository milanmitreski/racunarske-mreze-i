package v03.intro;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class InetIntro {
    public static void main(String[] args) {
        InetAddress inetAddress; // Predstavlja jednu IP adresu
        Inet4Address ipv4; // Predstavlja IPv4 adresu
        Inet6Address ipv6; // Predstavlja IPv6 adresu

        // Najcesce koristimo InetAddress, a Inet4Address i Inet6Address se koriste "u pozadini"

        try {
            InetAddress google = InetAddress.getByName("www.google.com");
            InetAddress random = InetAddress.getByName("208.201.239.101");
            // System.out.println(google);
            // System.out.println(random.getHostName());

            InetAddress[] addresses = InetAddress.getAllByName("www.google.com");
            System.out.println(Arrays.toString(addresses));

            // System.out.println(InetAddress.getLocalHost());

            InetAddress special = InetAddress.getByName("localhost");
            // System.out.println(special);

            // System.out.println(Arrays.toString(InetAddress.getByName("www.matf.bg.ac.rs").getAddress()));
            // System.out.println(InetAddress.getByName("www.matf.bg.ac.rs"));

            InetAddress ipv6addr = InetAddress.getByName("ipv6.google.com");
            // System.out.println(ipv6addr.getHostName());
            // System.out.println(ipv6addr.getCanonicalHostName()); // Ne treba koristiti
            // System.out.println(ipv6addr.getHostAddress());

            InetAddress matfFull = InetAddress.getByName("www.matf.bg.ac.rs");
            InetAddress matfShort = InetAddress.getByName("www.math.rs");

            System.out.println(matfShort);
            System.out.println(matfFull);

            // Za dva ili vise hostname se moze vezivati ista IP adresa

            System.out.println(matfFull.equals(matfShort)); // true jer predstavljaju istu ip adresu
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
