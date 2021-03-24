package av4;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Person implements Comparable<Person> {
    String name;
    Integer age;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Person(String line) {
        String[] parts = line.split("\\s+");
        this.name = parts[0];
        this.age = Integer.parseInt(parts[1]);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public int compareTo(Person o) {
        return this.age.compareTo(o.age);
    }
}

public class OldestPersonTest {
    public static List<Person> readPeopleFrom(InputStream inputStream) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
        return  bf.lines().map(line->new Person(line)).collect(Collectors.toList()) ;
//        List<Person> licnosti=new ArrayList<>();
//        String line;
//        while((line=bf.readLine())!=null){
//            String[] parts=line.split("\\s+");
//            Person p=new Person(parts[0], Integer.parseInt(parts[1]));
//            licnosti.add(p);
//        }
//        return licnosti;

    }

    public static void main(String[] args) throws IOException {
        File f = new File("C:\\Users\\toshiba\\IdeaProjects\\NaprednoProgramiranje\\src\\av4\\people.txt");
        try {
            List<Person> personList = readPeopleFrom(new FileInputStream(f));
            Person max=personList.stream().max(Comparator.naturalOrder()).get();
            System.out.println(max);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
