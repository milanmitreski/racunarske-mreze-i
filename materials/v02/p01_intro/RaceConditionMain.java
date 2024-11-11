package p01_intro;

final class RaceConditionMain {

    private static int x = 0;
    private static final int LIMIT = 100000000;

    private static class Test implements Runnable {
        public void run() {
            for (int j = 0; j < LIMIT; j++)
                x++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int TH_NUM = 10;

        for (int i = 0; i < TH_NUM; i++) {
            new Thread(new Test()).start();
        }

        Thread.sleep(5000);
        System.out.println("Expected: " + TH_NUM * LIMIT);
        System.out.println("Actual  : " + x);
    }
}
