package kolokviumski2code.Audicija;
import java.util.*;

class Participant1 {
    private String city;
    private String code;
    private String name;
    private int age;

    public Participant1(String city, String code, String name, int age) {
        this.city = city;
        this.code = code;
        this.name = name;
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant1 that = (Participant1) o;
        return code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }


    @Override
    public String toString() {
        //003 Аце 17
        return String.format("%s %s %d", this.code, this.name, this.age);
    }
}

class Audition {
    Map<String, Set<Participant1>> participantsByCity;

    public Audition() {
        this.participantsByCity = new HashMap<>();
    }

    public void addParticpant(String city, String code, String name, int age) {
        participantsByCity.putIfAbsent(city, new HashSet<>());
        participantsByCity.get(city).add(new Participant1(city, code, name, age));
    }

    public void listByCity(String city) {
        Set<Participant1> odcity = participantsByCity.get(city);
        odcity.stream().sorted(Comparator.comparing(Participant1::getName)
                .thenComparing(Participant1::getAge)
                .thenComparing(Participant1::getCode))
                .forEach(participant1 -> System.out.println(participant1));
    }
}


public class AuditionTest {
    public static void main(String[] args) {
        Audition audition = new Audition();
        List<String> cities = new ArrayList<String>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            if (parts.length > 1) {
                audition.addParticpant(parts[0], parts[1], parts[2],
                        Integer.parseInt(parts[3]));
            } else {
                cities.add(line);
            }
        }
        for (String city : cities) {
            System.out.printf("+++++ %s +++++\n", city);
            audition.listByCity(city);
        }
        scanner.close();
    }
}