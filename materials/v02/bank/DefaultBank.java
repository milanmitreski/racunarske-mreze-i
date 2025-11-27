package materials.v02.bank;

import java.util.Arrays;

public class DefaultBank implements IBank {

    private int[] accounts;

    DefaultBank(int accountsNum, int initialBalance) {
        this.accounts = new int[accountsNum];
        Arrays.fill(this.accounts, initialBalance);
        /* for(int i = 0; i < accountsNum; i++) {
            this.accounts[i] = initialBalance;
        } */
    }


    @Override
    public void transfer(int from, int to, int amount) {
        if(this.accounts[from] < amount)
            return;

        this.accounts[from] -= amount;
        this.accounts[to] += amount;
        System.out.println("Transfer from " + from + " to " + to + "; Amount: " + amount);

        System.out.println("Total balance: " + this.getTotalBalance());
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
