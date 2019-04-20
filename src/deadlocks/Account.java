package deadlocks;

public class Account {

    private int balance;

    public void deposit(int depositAmount) {
        this.balance += depositAmount;
    }

    public void withdraw(int depositAmount) {
        this.balance -= depositAmount;
    }

    public int getBalance() {
        return this.balance;
    }

    public static void transfer (Account account1, Account account2, int amount) {
        account1.withdraw(amount);
        account2.deposit(amount);
    }
}
