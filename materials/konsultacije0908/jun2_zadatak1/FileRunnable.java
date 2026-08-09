package konsultacije0908.jun2_zadatak1;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;

import static java.nio.file.Files.walk;

public class FileRunnable implements Runnable {
    public static String END_OF_WORK = "";
    private String cFileName;
    private BlockingQueue<String> fileQueue;

    FileRunnable(String cFileName, BlockingQueue<String> fileQueue) {
        this.cFileName = cFileName;
        this.fileQueue = fileQueue;
    }

    @Override
    public void run() {
        try {
            readCFiles(this.cFileName);
            this.fileQueue.put(END_OF_WORK);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void readCFiles(String cFileName) throws InterruptedException {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        new BufferedInputStream(
                                new FileInputStream(cFileName)
                        ),
                        StandardCharsets.US_ASCII
                )
        )) {
            String line;
            while((line = in.readLine()) != null) {
                this.fileQueue.put(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
