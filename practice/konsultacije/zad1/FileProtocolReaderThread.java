package konsultacije.zad1;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

public class FileProtocolReaderThread implements Runnable{

    private final URL url;
    private String tag;
    private final AtomicInteger counter;

    FileProtocolReaderThread(URL url,String tag, AtomicInteger counter) {
        this.url = url;
        this.tag = "<" + tag.trim() + ">";
        this.counter = counter;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.url.getFile()), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                int index = 0;
                while( (index = line.indexOf(this.tag,index)) != -1) {
                    this.counter.incrementAndGet();
                    index+= this.tag.length();
                }

            }
        } catch (FileNotFoundException e) {
            System.err.println("Fajl ne postoji");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
