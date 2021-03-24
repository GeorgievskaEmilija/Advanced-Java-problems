package av3.bank;

public class PlatinumCheckingAccount extends  Account implements InterestBearingAccount {
    public static final double RATE_PLATINUM=InterestCheckingAccount.RATE*2;
    public PlatinumCheckingAccount(String nameOwner, int number) {
        super(nameOwner, number);
    }

    @Override
    public void addInterest() {
        this.deposit(getBalance()*RATE_PLATINUM);
    }
}
