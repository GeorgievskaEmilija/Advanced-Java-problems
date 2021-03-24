package kolokviumski2code.Aerodromi;

import java.util.*;
import java.util.stream.Collectors;

class Flight {
    String from;
    String to;
    int time;
    int duration;

    public Flight(String from, String to, int time, int duration) {
        this.from = from;
        this.to = to;
        this.time = time;
        this.duration = duration;
    }

    @Override
    public String toString() {
        int a = duration / 60;
        int b = duration % 60;
        String traenje = a + "h" + b + "m";


        int vtorCas = time + duration;
        int hour2 = vtorCas / 60;
        int minutes2 = vtorCas % 60;
        String vreme = getPoletuvanje() + +hour2 + ":" + minutes2;

        return String.format("%s-%s %s %s\n", from, to, vreme, traenje);
    }


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPoletuvanje() {
        int hour = time / 60;
        int minutes = time % 60;
        String s = hour + ":" + minutes;
        return s;
    }
}

class Airport {
    String name;
    String country;
    String code;
    int passengers;
    Set<Flight> flightsFromThisAirport;

    public Airport(String name, String country, String code, int passengers) {
        this.name = name;
        this.country = country;
        this.code = code;
        this.passengers = passengers;
        flightsFromThisAirport = new TreeSet<>(Comparator.comparing(Flight::getTo).thenComparing(Flight::getPoletuvanje));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }


}

class Airports {
    Map<String, Airport> aerodromi;


    public Airports() {
        aerodromi = new HashMap<>();

    }

    public void addAirport(String name, String country, String code, int passengers) {
        Airport a = new Airport(name, country, code, passengers);
        aerodromi.putIfAbsent(code, a);
    }

    public void addFlights(String from, String to, int time, int duration) {
        Flight f = new Flight(from, to, time, duration);
        aerodromi.get(from).flightsFromThisAirport.add(f);

    }

    /*

London Heathrow (LHR)
United Kingdom
70037417
1. LHR-ATL 12:44-17:35 4h51m*/
    public void showFlightsFromAirport(String from) {
        Airport od = aerodromi.get(from);
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s (%s)\n", od.name, od.code));
        sb.append(String.format("%s\n", od.country));
        sb.append(String.format("%d\n", od.passengers));
        int k = 1;
        for (Flight f : od.flightsFromThisAirport) {
            sb.append(k + f.toString() + "\n");
            k++;
        }
        System.out.println(sb.toString());
    }

    public void showDirectFlightsFromTo(String from, String to) {
        List<Flight> letovi = aerodromi.get(from).flightsFromThisAirport
                .stream().filter(flight -> flight.to.equals(to)).collect(Collectors.toList());
        if (letovi.isEmpty() || letovi == null) {
            System.out.println("No flights from " + from + " to " + to);
        } else {
            letovi.forEach(System.out::println);
        }
        //No flights from IAH to FRA¶
    }

    public void showDirectFlightsTo(String to) {
        //site letovi do
        List<Flight> letovi = new ArrayList<>();
        aerodromi.values().stream()
                .forEach(a -> a.flightsFromThisAirport.stream().filter(b -> b.to.equals(to)).forEach(b -> letovi.add(b)));
        letovi.forEach(System.out::println);
    }
}

public class AirportsTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Airports airports = new Airports();
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] codes = new String[n];
        for (int i = 0; i < n; ++i) {
            String al = scanner.nextLine();
            String[] parts = al.split(";");
            airports.addAirport(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
            codes[i] = parts[2];
        }
        int nn = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < nn; ++i) {
            String fl = scanner.nextLine();
            String[] parts = fl.split(";");
            airports.addFlights(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
        }
        int f = scanner.nextInt();
        int t = scanner.nextInt();
        String from = codes[f];
        String to = codes[t];
        System.out.printf("===== FLIGHTS FROM %S =====\n", from);
        airports.showFlightsFromAirport(from);
        System.out.printf("===== DIRECT FLIGHTS FROM %S TO %S =====\n", from, to);
        airports.showDirectFlightsFromTo(from, to);
        t += 5;
        t = t % n;
        to = codes[t];
        System.out.printf("===== DIRECT FLIGHTS TO %S =====\n", to);
        airports.showDirectFlightsTo(to);
    }
}

// vashiot kod ovde



