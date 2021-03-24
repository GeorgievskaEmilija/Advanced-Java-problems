package av3.bank;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Account> accounts;

    public Bank() {
        this.accounts = new ArrayList<>();
    }



    public double totalAssets() {
        double totalBalance = 0;
        for (Account a : accounts) {
            totalBalance += a.getBalance();
        }
        return totalBalance;
    }

    public void addInterest() {
        for (Account a : accounts) {
            if (a instanceof InterestBearingAccount) {
                ((InterestBearingAccount) a).addInterest();
            }
        }
    }

}
