package materials.v02.bank;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockedBank implements IBank{

    private int[] accounts;

    private Lock lock;

    private Condition insufficientFunds;

    LockedBank(int accountsNum, int initialBalance) {
        this.accounts = new int[accountsNum];
        Arrays.fill(this.accounts, initialBalance);
        /* for(int i = 0; i < accountsNum; i++) {
            this.accounts[i] = initialBalance;
        } */

        this.lock = new ReentrantLock();
        this.insufficientFunds = this.lock.newCondition();
    }


    @Override
    public void transfer(int from, int to, int amount) {
        int total;
        this.lock.lock();
        try {
            while (this.accounts[from] < amount) {
                try {
                    this.insufficientFunds.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            this.accounts[from] -= amount;
            this.accounts[to] += amount;
            total = this.getTotalBalance();

            this.insufficientFunds.signalAll();
        } finally {
            this.lock.unlock();
        }

        System.out.println("Transfer from " + from + " to " + to + "; Amount: " + amount);
        System.out.println("Total balance: " + total);
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
