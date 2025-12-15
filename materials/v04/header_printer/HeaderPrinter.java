package v04.header_printer;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class HeaderPrinter {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()) {
            try {
                String line = sc.nextLine();
                if(line.trim().isEmpty())
                    continue;
                if(line.equalsIgnoreCase("exit"))
                    break;

                URL u = new URL(line);
                URLConnection uconn = u.openConnection();
                printHeaderFields(uconn);
            } catch (IOException e) {
                System.err.println("Greska pri obradi!");
            }
        }
    }

    private static void printHeaderFields(URLConnection uconn) {
        System.out.println("----------------------------------");
        System.out.println("Oth header: " + uconn.getHeaderField(0));
        for(int i = 1; ;i++) {
            String header = uconn.getHeaderField(i);
            if(header == null)
                break;
            System.out.println(uconn.getHeaderFieldKey(i) + ": " + header);
        }
        System.out.println("----------------------------------");
    }
}
