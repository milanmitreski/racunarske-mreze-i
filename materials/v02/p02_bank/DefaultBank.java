package p02_bank;

import java.util.Arrays;

final class DefaultBank implements IBank {

    // Contains an array of "accounts" - each account has some credit value
    private final int[] accounts;


    DefaultBank(int accountsNum, int initialBalance) {
        this.accounts = new int[accountsNum];
        Arrays.fill(this.accounts, initialBalance);
    }


    // The bank provides an option to transfer the funds between accounts
    // This function will be called from different threads, so there will be race condition
    @Override
    public void transfer(int from, int to, int amount) {
        if (this.accounts[from] < amount)
            return;

        System.out.println(Thread.currentThread());
        this.accounts[from] -= amount;
        this.accounts[to] += amount;
        System.out.printf("Transfer from %3d to %3d: %5d\n", from, to, amount);

        // Since there is a probability of a race condition there will be a change in the total sum
        System.out.println("Total balance: " + this.getTotalBalance());
    }

    @Override
    public int getTotalBalance() {
        // Arrays.stream() (Java collection streams, not to be confused with Streams from previous practice)
        // has plenty of useful methods which operate on collection streams and return streams which
        // can then be "collected" using the collect() method.
        return Arrays.stream(accounts).sum();
    }

    @Override
    public int count() {
        return accounts.length;
    }
}
