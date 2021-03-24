package kolokviumski1.Container;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

interface Weightable extends Comparable<Weightable> {
    public double getWeight();

    @Override
    default int compareTo(Weightable object) {
        return Double.compare(this.getWeight(), object.getWeight());
    }
}

class WeightableDouble implements Weightable {
    double weight;

    public WeightableDouble(double weight) {
        this.weight = weight;
    }

    @Override
    public double getWeight() {
        return weight;
    }
}

class WeightableString implements Weightable {
    String word;

    public WeightableString(String word) {
        this.word = word;
    }

    @Override
    public double getWeight() {
        return word.length();
    }
}

class Container<T extends Weightable> {
    //lista od T weightable elementi
    List<T> elements;

    public Container() {
        elements = new ArrayList<>();
    }

    void addElement(T item) {
        elements.add(item);
    }

    List<T> lighterThan(T elem) {
        return elements.stream().filter(e -> e.compareTo(elem) < 0).collect(Collectors.toList());
    }

    List<T> between(T a, T b) {
        return elements.stream()
                .filter(elem -> elem.compareTo(b) < 0 && elem.compareTo(a) > 0)
                .collect(Collectors.toList());
    }

    int compare(Container<? extends Weightable> two) {
        double weight1 = two.elements.stream().mapToDouble(elem -> elem.getWeight()).sum();
        double weight2 = this.elements.stream().mapToDouble(elem -> elem.getWeight()).sum();
        return Double.compare(weight1, weight2);

    }
}

public class ContainerApp {
    public static void main(String[] args) {
        Container<WeightableDouble> container1 = new Container();
        Container<WeightableDouble> container2 = new Container();
        Container<WeightableString> container3 = new Container();
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int p = sc.nextInt();
        double a = sc.nextDouble();
        double b = sc.nextDouble();
        WeightableDouble wa = new WeightableDouble(a);
        WeightableDouble wb = new WeightableDouble(b);
        for (int i = 0; i < n; i++) {
            double weight = sc.nextDouble();
            container1.addElement(new WeightableDouble(weight));
        }
        for (int i = 0; i < m; i++) {
            double weight = sc.nextDouble();
            container2.addElement(new WeightableDouble(weight));
        }
        for (int i = 0; i < p; i++) {
            String s = sc.next();
            container3.addElement(new WeightableString(s));
        }
        List<WeightableDouble> resultSmaller = container1.lighterThan(wa);
        List<WeightableDouble> resultBetween = container1.between(wa, wb);
        System.out.println("Lighter than " + wa.getWeight() + ":");
        for (WeightableDouble wd : resultSmaller) {
            System.out.println(wd.getWeight());
        }
        System.out.println("Between " + wa.getWeight() + " and " + wb.getWeight() + ":");
        for (WeightableDouble wd : resultBetween) {
            System.out.println(wd.getWeight());
        }
        System.out.println("Comparison: ");
        System.out.println(container1.compare(container2));
        System.out.println(container1.compare(container3));
    }
}
