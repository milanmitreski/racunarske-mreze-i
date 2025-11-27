package materials.v02.bank;

import java.util.concurrent.ThreadLocalRandom;

public class TransferRunnable implements Runnable {

    private IBank bank;
    private int from;
    private int max;

    private static final int SLEEP_DURATION = 2;

    TransferRunnable(IBank bank, int from, int max) {
        this.bank = bank;
        this.from = from;
        this.max = max;
    }

    @Override
    public void run() {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        try {
            while (true) {
                int to = r.nextInt(this.bank.clientCount());
                int amount = r.nextInt(this.max);
                this.bank.transfer(this.from, to, amount);
                Thread.sleep(SLEEP_DURATION);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
