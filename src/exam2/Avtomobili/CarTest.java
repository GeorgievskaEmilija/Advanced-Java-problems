package kolokviumski2code.Avtomobili;

import java.util.*;
import java.util.stream.Collectors;

class Car {
    String manufacturer;
    String model;
    int price;
    float power;
    static Comparator<Car> rastecki = Comparator.comparing(Car::getPrice).thenComparing(Car::getPower);
    static Comparator<Car> opagjacki = Comparator.comparing(Car::getPrice).reversed().thenComparing(Car::getPower).reversed();
    static Comparator<Car> modelrastecki = Comparator.comparing(Car::getModel);

    public Car(String manufacturer, String model, int price, float power) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.price = price;
        this.power = power;
    }

    @Override
    //Renault Clio (96KW) 12100
    public String toString() {
        return String.format("%s %s (%.0fKW) %d", manufacturer, model, power, price);
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public float getPower() {
        return power;
    }

    public void setPower(float power) {
        this.power = power;
    }
}

class CarCollection {
    List<Car> cars;

    public CarCollection() {
        cars = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void sortByPrice(boolean ascending) {
        if (ascending == true) {
            cars.sort(Car.rastecki);
        } else if(ascending==false) {
            cars.sort(Car.opagjacki);
        }
    }

    public List<Car> filterByManufacturer(String manufacturer) {
        return cars.stream().filter(car -> car.getManufacturer().equalsIgnoreCase(manufacturer))
                .sorted(Car.modelrastecki).collect(Collectors.toList());
    }

    public List<Car> getList() {
        return cars.stream().collect(Collectors.toList());
    }

}

public class CarTest {
    public static void main(String[] args) {
        CarCollection carCollection = new CarCollection();
        String manufacturer = fillCollection(carCollection);
        carCollection.sortByPrice(true);
        System.out.println("=== Sorted By Price ASC ===");
        print(carCollection.getList());
        carCollection.sortByPrice(false);
        System.out.println("=== Sorted By Price DESC ===");
        print(carCollection.getList());
        System.out.printf("=== Filtered By Manufacturer: %s ===\n", manufacturer);
        List<Car> result = carCollection.filterByManufacturer(manufacturer);
        print(result);
    }

    static void print(List<Car> cars) {
        for (Car c : cars) {
            System.out.println(c);
        }
    }

    static String fillCollection(CarCollection cc) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            if (parts.length < 4) return parts[0];
            Car car = new Car(parts[0], parts[1], Integer.parseInt(parts[2]),
                    Float.parseFloat(parts[3]));
            cc.addCar(car);
        }
        scanner.close();
        return "";
    }
}


// vashiot kod ovde
