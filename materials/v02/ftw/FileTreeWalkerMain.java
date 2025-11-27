package materials.v02.ftw;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class FileTreeWalkerMain {

    private static final int THREADS_NUM = 5;
    private static final int FILE_QUEUE_SIZE = 10;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter base dir: ");
        String baseDir = sc.nextLine();
        System.out.print("Enter keyword: ");
        String keyword = sc.nextLine();
        sc.close();

        BlockingQueue<Path> fileQueue = new ArrayBlockingQueue<>(FILE_QUEUE_SIZE);

        FileTreeWalkerRunnable ftw = new FileTreeWalkerRunnable(Paths.get(baseDir), fileQueue);
        new Thread(ftw).start();

        for(int i = 0; i < THREADS_NUM; i++) {
            SearchFileRunnable sf = new SearchFileRunnable(keyword, fileQueue);
            new Thread(sf).start();
        }
    }
}
