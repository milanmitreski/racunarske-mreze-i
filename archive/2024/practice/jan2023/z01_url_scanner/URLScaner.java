package practice.jan2023.z01_url_scanner;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;

public class URLScaner {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while(in.hasNext()) {
            String input = in.nextLine();
            try {
                URL url = new URL(input);
                InetAddress ip = InetAddress.getByName(url.getHost());
                if(ip instanceof Inet4Address && ip.getHostAddress().equals(url.getHost())) {
                    System.out.println("(v4) " + url.getProtocol() + " " + url.getPath() + " " + Arrays.toString(ip.getAddress()));
                } else if (ip instanceof Inet6Address && ip.getHostAddress().equals(url.getHost().substring(1, url.getHost().length()-1))) {
                    /*
                        Manji problem: formatiranje IPv6 adresa pri unosu mora biti sledece:
                            http://[1080:0:0:0:8:800:200C:417A]/index.html
                            tj. nule se moraju navoditi i celokupna IPv6 adresa se zadaje u okviru uglastih zagrada
                        Ovo je sa zvanicne Javine dokumentacije i RFC standarda
                        Pritom, moramo pri poredjenju host dela URL-a sa ip adresom skloniti uglaste zagrade
                    */
                    System.out.println("(v6) " + url.getProtocol() + " " + url.getPath());
                } else {
                    System.out.println(url.getProtocol() + " " + url.getAuthority() + " " + url.getPath());
                }
            } catch (MalformedURLException | UnknownHostException e) {
                System.out.println("Malformed URL. Please enter a valid URL!.");
            }
        }
        in.close();
    }
}
