package av3.lambda;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LambdaTest {
    public static void main(String[] args) {
        Supplier<Integer> integerSupplier=() -> new Random().nextInt();
        System.out.println(integerSupplier.get());


        Interfejs i = (a, b) -> a + b;
        System.out.println(i.doOperation(6, 5));
    }
}
