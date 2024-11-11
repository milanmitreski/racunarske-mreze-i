package p01_intro;

final class ThreadsIntroMain {

    // There are two ways to run something in a different thread in Java
    //      1. extend Thread class
    //      2. implement Runnable interface

    /* *************** 1. *************** */

    private static class MyThread extends Thread {
        @Override
        public void run() {
            // override method run with your logic

            try {
                // Why do we need to catch InterruptedException?
                // Every call that blocks in Java requires the caller to 
                // catch this exception - so that the thread can finalize
                // its work safely before being terminated
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread());
        }
    }

    /* ********************************** */

    /* *************** 2. *************** */

    private static class MyRunnableThread implements Runnable {
        @Override
        public void run() {
            // override method run with your logic

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread());
        }
    }

    /* ********************************** */

    // What is the difference? If the model of the application is such that you need
    // to subclass another class, then you must implement Runnable because multiple
    // inheritance is not allowed in Java

    // How to use these classes?

    public static void main(String[] args) {
        System.out.println(Thread.currentThread());

        Thread t1 = new MyThread();
        t1.run();   // WRONG
        t1.start(); // CORRECT - this sets up the actual system thread and invokes run() method

        Thread t2 = new Thread(new MyRunnableThread());
        t2.start();

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                // override method run with your logic
            }
        });

        Thread t4 = new Thread(() -> {
            // override method run with your logic
        });

        Thread t5 = new Thread(t1);


        // Why don't we wait for these threads to finish?
        // main() is clearly exiting before the threads finish...
        System.out.println("main() finished...");

        // What if we do this instead?
        // System.exit(0);
    }

}
