package konsultacije.zad1;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class TagCounter {

    public static void main(String[] args) {

        String path = "/home/miloje/Desktop/Vezbe/RM/Casovi/src/konsultacije/zad1/urls.txt";
        String tag;

        try (Scanner sc = new Scanner(System.in);
             BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {

            List<Thread> threadList = new ArrayList<>();
            tag  = sc.nextLine();
            AtomicInteger counter = new AtomicInteger(0);

            String line;
            int countLines = 0;

            while((line = input.readLine()) != null) {
                try {
                    URL url = (new URI(line)).toURL();
                    if (url.getProtocol().toLowerCase() == "file" && url.toString().endsWith(".html")) {
                        FileProtocolReaderThread t = new FileProtocolReaderThread(url, tag, counter);
                        Thread task = new Thread(t);
                        task.start();
                        threadList.add(task);
                    }
                } catch (URISyntaxException | IllegalArgumentException e) {
                }
                countLines++;
            }

            for (Thread t: threadList) {
                t.join();
            }

            System.out.println("Ukupnan broj tagova je: " + counter);
            System.out.println("Broj linija u url.txt je " + countLines);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
