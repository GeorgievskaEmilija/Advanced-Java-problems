package kolokviumski1.F1Race;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Pilot implements Comparable<Pilot> {
    String name;
    String bestlap;

    public Pilot() {

    }

    public static Pilot returnPilot(String line) {
        String[] parts = line.split(" ");
        String nameP = parts[0];
        String lap1 = parts[1];
        String lap2 = parts[2];
        String lap3 = parts[3];
        Pilot p = new Pilot();
        p.name = nameP;
        if (lap1.compareTo(lap2) < 0) {
            if (lap1.compareTo(lap3) < 0) {
                p.bestlap = lap1;
            } else {
                p.bestlap = lap3;
            }
        } else {
            if (lap2.compareTo(lap3) < 0) {
                p.bestlap = lap2;
            } else {
                p.bestlap = lap3;
            }
        }
        return p;
    }

    @Override
    public String toString() {
        return String.format("%-10s %9s", name, bestlap);
    }

    @Override
    public int compareTo(Pilot o) {
        return this.bestlap.compareTo(o.bestlap);
    }
}

class F1Race {
    List<Pilot> pilots;

    public F1Race() {
        pilots = new ArrayList<>();
    }

    public void readResults(InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        pilots = br.lines().map(line -> Pilot.returnPilot(line)).collect(Collectors.toList());

    }

    public void printSorted(PrintStream out) {
        pilots.sort(Comparator.naturalOrder());
        PrintWriter pw = new PrintWriter(out);
        int i = 1;
        for (Pilot p : pilots) {
            pw.print(i + ". ");
            pw.println(p);
            i++;
        }
        pw.close();
    }

}

public class F1RaceTest {

    public static void main(String[] args) throws FileNotFoundException {
        F1Race f1Race = new F1Race();
        File f = new File("C:\\Users\\toshiba\\IdeaProjects\\NaprednoProgramiranje\\src\\kolokviumski1\\F1Race\\f1racefile.txt");
        f1Race.readResults(new FileInputStream(f));
        f1Race.printSorted(System.out);
    }

}



