package materials.v02.ftw;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;

public class FileTreeWalkerRunnable implements Runnable {
    public static Path END_OF_WORK = Paths.get("");
    private Path baseDir;
    private BlockingQueue<Path> fileQueue;

    FileTreeWalkerRunnable(Path baseDir, BlockingQueue<Path> fileQueue) {
        this.baseDir = baseDir;
        this.fileQueue = fileQueue;
    }

    @Override
    public void run() {
        try {
            walk(this.baseDir);
            this.fileQueue.put(END_OF_WORK);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void walk(Path dir) throws InterruptedException {
        try (DirectoryStream<Path> dirs = Files.newDirectoryStream(dir)) {
            for(Path path : dirs) {
                if(Files.isDirectory(path))
                    walk(path);
                else
                    this.fileQueue.put(path);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
