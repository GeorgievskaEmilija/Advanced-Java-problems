package kolokviumski1.GenerickaTrojka;

import java.util.*;

class Triple<T extends Number> {
    List<T> nums;

    public Triple(T num1, T num2, T num3) {
        nums = new ArrayList<>();
        nums.add(num1);
        nums.add(num2);
        nums.add(num3);

    }

    double max() {
        return nums.stream()
                .mapToDouble(n -> n.doubleValue())
                .max().getAsDouble();
    }

    double avarage() {
        return nums.stream().mapToDouble(n -> n.doubleValue()).average().getAsDouble();

    }

    void sort() {
        nums.sort(Comparator.comparing(T::doubleValue));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%.2f %.2f %.2f", nums.get(0).doubleValue(),
                nums.get(1).doubleValue(), nums.get(2).doubleValue()));
        return sb.toString();
    }
}

public class TripleTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        Triple<Integer> tInt = new Triple<Integer>(a, b, c);
        System.out.printf("%.2f\n", tInt.max());
        System.out.printf("%.2f\n", tInt.avarage());
        tInt.sort();
        System.out.println(tInt);
        float fa = scanner.nextFloat();
        float fb = scanner.nextFloat();
        float fc = scanner.nextFloat();
        Triple<Float> tFloat = new Triple<Float>(fa, fb, fc);
        System.out.printf("%.2f\n", tFloat.max());
        System.out.printf("%.2f\n", tFloat.avarage());
        tFloat.sort();
        System.out.println(tFloat);
        double da = scanner.nextDouble();
        double db = scanner.nextDouble();
        double dc = scanner.nextDouble();
        Triple<Double> tDouble = new Triple<Double>(da, db, dc);
        System.out.printf("%.2f\n", tDouble.max());
        System.out.printf("%.2f\n", tDouble.avarage());
        tDouble.sort();
        System.out.println(tDouble);
    }
}
// vasiot kod ovde
// class Triple


