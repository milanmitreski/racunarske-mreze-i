package p05_pooled_weblog;

import java.io.*;
import java.util.*;

// This class has the task to process the log file line-by-line and use
// threads which will process one line at a time. The log file is provided
// via InputStream and the output will be sent to a PrintStream
final class PooledWeblog implements AutoCloseable {
    private final BufferedReader in;
    private final BufferedWriter out;
    private final int numOfThreads;
    private boolean finished;
    private final List<Thread> workers;

    // Java has a neat way of handling race condition when it comes to collections.
    // Under Collections class there are various factory methods which produce
    // synchronized collections (we need to pass the collection we want to
    // "synchronize" as an argument). In this case we wish to synchronize a
    // LinkedList (reminder: List is an interface so we cannot instantiate a List,
    // so we can only use classes that implement List interface - such as LinkedList).
    // Of course, we can use other collection types like synchronized maps, sets etc.
    // depending on what we need. When you use synchronized collections, operations are
    // thread-safe. Keep in mind that iterating over a collection IS NOT thread-safe,
    // so you need to manually provide synchronization in those cases.
    private final List<String> entries = Collections.synchronizedList(new LinkedList<>());


    PooledWeblog(InputStream in, PrintStream out, int threads) {
        this.in = new BufferedReader(new InputStreamReader(in));
        this.out = new BufferedWriter(new OutputStreamWriter(out));
        this.numOfThreads = threads;
        this.workers = new ArrayList<>(this.numOfThreads);
    }


    void processLogFile() {

        // Starting the lookup threads
        for (int i = 0; i < this.numOfThreads; i++) {
            Thread worker = new Thread(new LookupRunnable(this));
            worker.start();
            this.workers.add(worker);
        }

        try {
            // Reading the log file line by line
            for (String entry = in.readLine(); entry != null; entry = in.readLine()) {
                // Since log file is very large and because threads will need more time
                // to do their logic than it takes to read a line, the list will grow
                // in size. We do not want for our list to too become large so we wait
                // a bit if the list size is greater than threads amount so threads
                // can catch up
                while (this.entries.size() > this.numOfThreads) {
                    try {
                        Thread.sleep(1000 / this.numOfThreads);
                    } catch (InterruptedException ex) {
                        this.finished = true;
                        System.err.println("Work interrupted! Signalling...");
                        synchronized (this.entries) {
                            this.entries.notifyAll();
                        }
                    }
                }

                // We can operate on synchronized collections using a synchronized block
                synchronized (this.entries) {
                    this.entries.add(0, entry);

                    // Threads will wait if the list is empty, so after each addition
                    // we will notify them. In order to wait/notify we MUST be inside
                    // a synchronized block (similar to holding locks when awaiting or
                    // signalling a condition
                    this.entries.notifyAll();
                }

                // Yielding means that the thread is not doing anything particularly
                // important and if any other threads or processes need to be run,
                // they should run. Otherwise, the current thread will continue
                Thread.yield();
            }

            // Signalize threads that the work is finished
            this.finished = true;
            System.err.println("Work finished! Signalling...");
            synchronized (this.entries) {
                this.entries.notifyAll();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void log(String entry) throws IOException {
        this.out.write(entry);
        this.out.newLine();
        // It is also possible to get the newline system property via:
        // System.getProperty("line.separator", "\r\n")
        // "/r/n" is default value in case the property isn't set
        this.out.flush();
    }

    boolean isFinished() {
        return this.finished;
    }

	List<String> getEntries() {
		return this.entries;
	}

    @Override
    public void close() throws Exception {
        this.in.close();

        // We have to wait for workers to finish in order to close the output stream (they use it for logging)
        for (Thread worker : this.workers) {
            worker.join();
        }
        this.out.close();
    }
}
