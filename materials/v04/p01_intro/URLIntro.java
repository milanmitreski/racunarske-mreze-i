package url.g1.intro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class URLIntro {

    // URL - Uniform Resource Locator
    // protocol://userInfo@hostName:port/path?query#fragment(anchor)

    public static void main(String[] args) throws IOException {
        URL u = new URL("file://localhost//Users/milanmitreski/myproject.grm");
        // URL konstruktor prima string i PARSIRA ga da proveri da li
        // je sve u redu sa samom URL adresom i da li JVM podrzava
        // rad sa datim protokolom. Ne otvara konekciju!!!
        System.out.println(u.getDefaultPort());
        // svaki protokol ima defaultni port i ukoliko ne definisemo port
        // u samoj URL-u, koristi se defaultni port
        System.out.println(u.getQuery());
        // query je deo URL kojim dajemo dodatne argumente koje koristi
        // web server za specificnije pretrazivanje
        System.out.println(u.getUserInfo());
        // u zavisnosti od toga da li je potrebna neka identifikacija
        // ovde dajemo user info
        System.out.println(u.getPath());
        // putanja do resursa
        System.out.println(u.getHost());
        // ime hosta na kom se nalazi resurs
        System.out.println(InetAddress.getByName(u.getHost()));

        // Prvo, pokusavamo rad sa InputStream-om
        /* try (
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                u.openStream()
                        )
                )
        ) {
            char[] buf = new char[512];
            int charsRead;
            while((charsRead = in.read(buf, 0, 512)) != -1) {
                System.out.print(buf);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } */

        // Drugo, pokusajmo sa URLConnection
        // URLConnection je klasa koja enkapsulira sve podatke
        // u vezi konekcije sa odredjenim URL-om
        URLConnection uconn = u.openConnection();
        // Ovde se salje zahtev host-u da dostavi potrebni resurs

        System.out.println(uconn.getHeaderFields());
        // Header-i su deo odgovora koji predstavljaju metapodatke o samoj
        // komunikaciji i kako je zahtev prosao

        try (
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                uconn.getInputStream()
                        )
                )
        ) {
            char[] buf = new char[512];
            int charsRead;
            while((charsRead = in.read(buf, 0, 512)) != -1) {
                System.out.print(buf);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
