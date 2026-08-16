package konsultacije1608.sept1_25_zadatak1;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String IP_FILE_NAME = "materials/konsultacije1608/jul_25_zadatak2/ips.txt";
    private static final String REPORT_FILE_NAME = "materials/konsultacije1608/jul_25_zadatak2/protocol_stats.txt";
    private static List<InetAddress> hosts;
    private static List<PortThread> workers;


    static void main() {
        loadHosts();
        startThreads();
        printReport();
    }

    private static void loadHosts() {
        hosts = new ArrayList<>();

        try(Scanner in = new Scanner(
                new BufferedInputStream(
                        new FileInputStream(IP_FILE_NAME)
                )
        )) {
            while(in.hasNextLine()) {
                String line = in.nextLine();
                try {
                    InetAddress address = InetAddress.getByName(line);
                    hosts.add(address);
                } catch (UnknownHostException e) {
                    System.out.println("Unknown host: " + line + ". Skipped.");
                }
            }
            System.out.println("Hosts loaded.");
        } catch (FileNotFoundException e) {
            System.out.println("TXT file not found. IPs not loaded.");
        }
    }

    private static void startThreads() {
        workers = new ArrayList<>();

        workers.add(new PortThread(80, "HTTP", hosts));
        workers.add(new PortThread(443, "HTTPS", hosts));
        workers.add(new PortThread(21, "FTP", hosts));
        workers.add(new PortThread(22, "SSH", hosts));
        workers.add(new PortThread(25, "SMTP", hosts));

        for(PortThread t : workers) {
            System.out.println("Thread " + t + " started.");
            t.start();
        }
    }

    private static void printReport() {
        StringBuilder reportBuilder = new StringBuilder();

        for(PortThread t : workers) {
            try {
                t.join();
                reportBuilder.append(t.getReport()).append("\n");
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted.");
            }
        }

        String report = reportBuilder.toString().trim();

        try(PrintStream out = new PrintStream(
                new BufferedOutputStream(
                        new FileOutputStream(
                                REPORT_FILE_NAME
                        )
                ),
                true
        )) {
            out.println(report);
        } catch (FileNotFoundException e) {
            System.out.println("TXT file not found. Report not printed.");
        }
    }

}
