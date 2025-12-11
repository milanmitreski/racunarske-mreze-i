package v03.exam_jan2025;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Map<String, List<String>> ipsToDomains = new HashMap<>(); // 147.91.66.73 -> ["www.matf.bg.ac.rs", "www.math.rs"]
        try (
                Scanner in = new Scanner(
                        new FileInputStream("materials/v03/exam_jan2025/domains.txt")
                );
                PrintStream out = new PrintStream(
                        new BufferedOutputStream(
                                new FileOutputStream("materials/v03/exam_jan2025/ip.with.domains.txt")
                        ),
                        true
                )
        ) {
            while(in.hasNextLine()) {
                String domain = in.nextLine();
                try {
                    InetAddress addr = InetAddress.getByName(domain);

                    if(!ipsToDomains.containsKey(addr.getHostAddress())) {
                        ipsToDomains.put(addr.getHostAddress(), new ArrayList<>());
                    }

                    List<String> current = ipsToDomains.get(addr.getHostAddress());
                    current.add(domain);
                    ipsToDomains.put(addr.getHostAddress(), current);
                } catch (UnknownHostException e) {
                    System.err.println("Can not access " + domain);
                }
            }

            for(Map.Entry<String, List<String>> entry : ipsToDomains.entrySet()) {
                out.println(entry.getKey());
                out.println("- Domains: ");
                for(String d : entry.getValue()) {
                    out.print("--- ");
                    out.println(d);
                }
                out.println();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
