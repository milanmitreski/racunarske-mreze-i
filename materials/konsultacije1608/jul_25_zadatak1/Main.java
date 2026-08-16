package konsultacije1608.jul_25_zadatak1;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final static String FILE_NAME = "materials/konsultacije1608/jul_zadatak1/filter.txt";
    private static List<InetAddress> forbiddenHosts;


    static void main() {
        loadForbiddenHosts();
        filterURLs();
    }

    private static void loadForbiddenHosts() {
        forbiddenHosts = new ArrayList<>();

        try(Scanner in = new Scanner(
                new BufferedInputStream(
                        new FileInputStream(FILE_NAME)
                )
        )) {
            while(in.hasNextLine()) {
                String line = in.nextLine();
                try {
                    InetAddress address = InetAddress.getByName(line);
                    forbiddenHosts.add(address);
                } catch (UnknownHostException e) {
                    System.out.println("Unknown host: " + line + ". Skipped.");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("TXT file not found. Filter not set up.");
        }
    }

    private static void filterURLs() {
        Scanner in = new Scanner(System.in);
        String message = "Unesite URL:\t";

        System.out.println(message);
        while(in.hasNextLine()) {
            String line = in.nextLine();
            try {
                URL u = new URL(line);
                InetAddress address = InetAddress.getByName(u.getHost());
                if(!forbiddenHosts.contains(address))
                    System.out.println("Dozvoljeno.");
                else
                    System.out.println("Nedozvoljeno.");
            } catch (MalformedURLException e) {
                // Nepodrzan protokol ili losa forma URL
                // sftp://....
                // //www.matf.bg.ac.rs:sftp/5000 asda
                System.out.println("Protokol nije podrzan ili je unesen URL u pogresnom formatu.");
            } catch (UnknownHostException e) {
                System.out.println("Nepoznat host.");
            }
            System.out.println(message);
        }
    }
}
