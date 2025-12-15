package v04.intro;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class URLIntro {

    // URL - Uniform Resource Locator
    // protocol://userInfo@hostName:port/path?query#fragment(anchor)

    public static void main(String[] args) throws IOException {
        try {
            URL u = new URL("https://www.matf.bg.ac.rs/studije/osnovne-studije/osnovne-informatika/");
            URL u_full = new URL("ftp://mi23735@alas.matf.bg.ac.rs:300/materijali/rm.pdf?verzija=2#cas4");
            // URL konstruktor kao argument dobija string i PARSIRA ga
            // da proveri da li predstavlja jednu validnu URL adresu
            // i da li JVM podrzava rad sa datim protokolom
            // Ne pristupa internetu!

            printInfo(u);
            printInfo(u_full);

            // Ovde prvi put pristupamo internetu
            System.out.println(InetAddress.getByName(u.getHost()));
            System.out.println(InetAddress.getByName(u_full.getHost()));

            // Citanje sa URL

            // 1. varijanta - openStream
            /* try(Scanner sc = new Scanner(
                    new BufferedInputStream(u.openStream())
                )
            ) {
                while(sc.hasNextLine()) {
                    System.out.println(sc.nextLine());
                }
            } catch (IOException e) {
                System.err.println("Greska pri otvaranju URL konekcije");
            } */

            URLConnection uconn = u.openConnection();
            System.out.println(uconn.getHeaderFields());
            try(Scanner sc = new Scanner(
                    new BufferedInputStream(uconn.getInputStream())
            )
            ) {
                while(sc.hasNextLine()) {
                    System.out.println(sc.nextLine());
                }
            } catch (IOException e) {
                System.err.println("Greska pri otvaranju URL konekcije");
            }
        } catch (MalformedURLException e) {
            // Ispaljuje se MalformedURLException u slucaju da:
            // 1. Struktura URL nije zadata na ispravan nacin
            // 2. JVM ne podrzava rad sa navedenim protokolom
            System.err.println("Greska pri radu sa URL");
        } catch (UnknownHostException e) {
            System.err.println("Greska pri radu sa Inet");
        }
    }

    private static void printInfo(URL u) {
        System.out.println(u.getPath());
        System.out.println(u.getPort());
        System.out.println(u.getDefaultPort());
        System.out.println(u.getUserInfo());
        System.out.println(u.getHost());
        System.out.println(u.getProtocol());
        System.out.println("----------------------------------");
    }
}
