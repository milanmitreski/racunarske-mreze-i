package materials.v02.intro;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RaceConditionIntro {

    private static AtomicInteger x = new AtomicInteger(0);
    private static int LIMIT = 1000000;

    private static class Test implements Runnable {

        @Override
        public void run() {
            for(int i = 0; i < LIMIT; i++) {
                x.incrementAndGet();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int TH_NUM = 10;

        List<Thread> threads = new ArrayList<>();

        for(int i = 0; i < TH_NUM; i++) {
            Thread t = new Thread(new Test());
            t.start();
            threads.add(t);
        }

        for(Thread t : threads) {
            t.join();
        }

        System.out.println("Expected: " + TH_NUM * LIMIT);
        System.out.println("Actual: " + x.get());
    }
}
