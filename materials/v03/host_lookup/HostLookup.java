package v03.host_lookup;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class HostLookup {
    public static void main(String[] args) {
        // Korisnik unosi
        //  1. IP adresu -- ispisujemo hostname
        //  2. Hostname -- ispisujemo IP adresu

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter names and IP addressses. Enter \"exit\" to quit.");

        while(true) {
            String host = sc.nextLine();
            if(host.equalsIgnoreCase("exit")) // ExIt
                break;
            System.out.println(lookup(host));
        }
    }

    private static String lookup(String host) {
        InetAddress node;
        try {
            node = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            return "Cannot find host " + host;
        }

        if(isHostname(host)) {
            return node.getHostAddress();
        } else { // isIPaddr(host)
            return node.getHostName();
        }
    }

    private static boolean isHostname(String host) {
        if(host.indexOf(':') != -1) // Ukoliko se : nalazi u host, vrati false jer se radi o IPv6 adresi
            return false;

        if(host.split("\\.").length != 4)
            return true;

        // ovde znamo da je host oblika ___.___.___.___

        for(int i = 0; i < host.length(); i++) {
            char current = host.charAt(i); // current = host[i]
            if(current != '.' && !Character.isDigit(current)) {
                // Nije . i nije cifra -- ne moze IPv4
                return true;
            }
        }

        return false;
    }

}
