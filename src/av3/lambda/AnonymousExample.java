package av3.lambda;

import av3.bank.InterestBearingAccount;

public class AnonymousExample {
    Interfejs Addition =new Interfejs() {
        @Override
        public double doOperation(double x, double y) {
            return x+y;
        }
    };
}
