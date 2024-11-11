package p03_blocking_queue;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;

final class FileTreeWalkerRunnable implements Runnable {

    // Since we need a way of stopping the threads, our directory walker will put this
    // special value in the queue, so the threads know when to stop working.
    // We cannot use `null` because `put()` method requires the element to not be null.
    static final Path END_OF_WORK = Paths.get("");

    private final BlockingQueue<Path> queue;
    private final Path startingDir;


    FileTreeWalkerRunnable(Path path, BlockingQueue<Path> queue) {
        this.queue = queue;
        this.startingDir = path;
    }


    @Override
    public void run() {
        try {
            walk(this.startingDir);

            // We put the special var here, since we have finished the traversal
            this.queue.put(END_OF_WORK);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void walk(Path startingDir) throws InterruptedException {
        // try-with-resources statement
        // Resources declared inside the parentheses will be automatically closed once
        // they are no longer needed. You can only declare variables of a type that implements
        // AutoCloseable interface.
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(startingDir)) {
            // Directory stream gives us a stream of files in the given directory, which is iterable
            for (Path p : ds) {
                if (Files.isDirectory(p))
                    walk(p);	        // recursive search
                else
                    this.queue.put(p);  // blocks if the queue is full
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
