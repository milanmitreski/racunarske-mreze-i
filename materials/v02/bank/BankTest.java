package materials.v02.bank;

public class BankTest {
    private static final int ACCOUNTS = 5;
    private static final int BALANCE = 100;

    public static void main(String[] args) {
        // IBank bank = new DefaultBank(ACCOUNTS, BALANCE);
        // IBank bank = new LockedBank(ACCOUNTS, BALANCE);
        IBank bank = new SynchronizedBank(ACCOUNTS, BALANCE);
        for(int i = 0; i < ACCOUNTS; i++) {
            TransferRunnable r = new TransferRunnable(bank, i, 20);
            Thread t = new Thread(r);
            t.start();
        }
    }
}
