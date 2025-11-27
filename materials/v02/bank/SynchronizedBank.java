package materials.v02.bank;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedBank implements IBank{
    private final int[] accounts;

    SynchronizedBank(int accountsNum, int initialBalance) {
        this.accounts = new int[accountsNum];
        Arrays.fill(this.accounts, initialBalance);
        /* for(int i = 0; i < accountsNum; i++) {
            this.accounts[i] = initialBalance;
        } */
    }


    /* @Override
    public synchronized void transfer(int from, int to, int amount) {
        while (this.accounts[from] < amount) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        this.accounts[from] -= amount;
        this.accounts[to] += amount;

        System.out.println("Transfer from " + from + " to " + to + "; Amount: " + amount);
        System.out.println("Total balance: " + getTotalBalance());
        System.out.println(); // new line

        this.notifyAll();
    } */

    @Override
    public void transfer(int from, int to, int amount) {
        synchronized (this) {
            while (this.accounts[from] < amount) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            this.accounts[from] -= amount;
            this.accounts[to] += amount;
            this.notifyAll();
        }

        System.out.println("Transfer from " + from + " to " + to + "; Amount: " + amount);
        System.out.println("Total balance: " + getTotalBalance());
        System.out.println(); // new line
    }

    @Override
    public int getTotalBalance() {
        int total = 0;
        for(int i = 0; i < this.clientCount(); i++) {
            total += this.accounts[i];
        }
        return total;
    }

    @Override
    public int clientCount() {
        return this.accounts.length;
    }
}
