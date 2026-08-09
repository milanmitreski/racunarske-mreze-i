package konsultacije0908.jun2_zadatak1;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ReadLinesRunnable implements Runnable {

    private BlockingQueue<String> fileQueue;
    private AtomicInteger totalLines;

    ReadLinesRunnable(AtomicInteger totalLines, BlockingQueue<String> fileQueue) {
        this.totalLines = totalLines;
        this.fileQueue = fileQueue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String file_path = this.fileQueue.take();
                if(file_path.equals(FileRunnable.END_OF_WORK)) {
                    this.fileQueue.put(file_path);
                    break;
                }
                this.countLines(file_path);
            }
        } catch (InterruptedException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void countLines(String file_path) throws MalformedURLException {
        if(!file_path.endsWith(".c"))
            return;

        File f = new File(file_path);
        URL url = f.toURI().toURL();

        System.out.println("url: " + file_path);

        try (
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                new BufferedInputStream(
                                        url.openConnection().getInputStream()
                                ),
                                StandardCharsets.US_ASCII
                        )
                );
        ) {
            int lines = 0;
            while(in.readLine() != null) {
                lines++;
            }
            totalLines.addAndGet(lines);
        } catch (IOException e) {
            System.out.println("missing: " + file_path);
        }
    }
}
