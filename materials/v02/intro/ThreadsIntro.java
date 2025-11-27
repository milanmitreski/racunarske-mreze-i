package materials.v02.intro;

public class ThreadsIntro {

    /* ---- 1. nacin ---- */
    private static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("Thread: " + Thread.currentThread());
        }
    }

    /* ---- 2. nacin ---- */
    private static class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("Runnable: " + Thread.currentThread());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new MyThread();
        t1.start(); // Pokretanje zasebne niti

        Runnable r = new MyRunnable();
        Thread t2 = new Thread(r);
        t2.start(); // Pokretanje zasebne niti

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Nasilno prekinute niti");
        }

        System.out.println("Main: " + Thread.currentThread());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
