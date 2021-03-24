package av3.bank;

public class InterestCheckingAccount extends Account implements InterestBearingAccount {
    public static final double RATE=0.03;

    public InterestCheckingAccount(String nameOwner, int number) {
        super(nameOwner, number);
    }

    @Override
    public void addInterest() {
        this.deposit(this.getBalance()*RATE);
    }
}
