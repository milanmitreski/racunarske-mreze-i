package konsultacije0908.jun2_zadatak1;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static final int THREADS_NUM = 5;
    private static final int FILE_QUEUE_SIZE = 10;
    private static final String C_FILE_NAME = "materials/konsultacije0908/jun2_zadatak1/c_fajlovi.txt";

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> fileQueue = new ArrayBlockingQueue<>(FILE_QUEUE_SIZE);
        AtomicInteger totalLines = new AtomicInteger(0);

        FileRunnable ftw = new FileRunnable(C_FILE_NAME, fileQueue);
        new Thread(ftw).start();

        List<Thread> threadList = new ArrayList<>();
        for(int i = 0; i < THREADS_NUM; i++) {
            ReadLinesRunnable sf = new ReadLinesRunnable(totalLines, fileQueue);
            Thread t = new Thread(sf);
            t.start();
            threadList.add(t);
        }

        for(Thread thread : threadList) {
            thread.join();
        }

        System.out.println("result: " + totalLines.get());
    }
}
