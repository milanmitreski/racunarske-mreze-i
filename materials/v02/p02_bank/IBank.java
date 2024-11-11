package p02_bank;

// Using interface because we will have multiple examples of Banks
// having different synchronization techniques
interface IBank {
    void transfer(int from, int to, int amount);
    int getTotalBalance();
    int count();
}
