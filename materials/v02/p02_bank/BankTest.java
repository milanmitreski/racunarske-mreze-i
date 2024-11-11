package p02_bank;

final class BankTest {
    private static final int ACCOUNTS = 5;
    private static final int STARTING_BALANCE = 1000000;

    public static void main(String[] args) {
        IBank bank = new DefaultBank(ACCOUNTS, STARTING_BALANCE);
//         IBank bank = new LockedBank(ACCOUNTS, STARTING_BALANCE);
//         IBank bank = new SynchronizedBank(ACCOUNTS, STARTING_BALANCE);

        for (int i = 0; i < ACCOUNTS; i++) {
            TransferRunnable transfer = new TransferRunnable(bank, i, 10);
            Thread t = new Thread(transfer);
            t.start();
        }
    }
}
