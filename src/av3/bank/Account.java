package av3.bank;

public abstract class Account {
    private String nameOwner;
    private int number;
    private double balance;

    public Account(String nameOwner, int number) {
        this.nameOwner = nameOwner;
        this.number = number;
        this.balance = 0.0;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public void withdraw(double amount) {
        if (this.balance < amount) {
            System.out.println("you dont have that much money!");
        } else {
            this.balance -= amount;
        }
    }

}
