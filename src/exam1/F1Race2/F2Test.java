package kolokviumski1.F1Race2;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class Pilot implements Comparable<Pilot>{
    String name;
    String bestLap;

    public Pilot(String name) {
        this.name = name;
    }

    public static Pilot createPilot(String line) {
        String [] parts=line.split("\\s+");
        Pilot p=new Pilot(parts[0]);
        String lap1=parts[1];
        String lap2=parts[2];
        String lap3=parts[3];
        if(lap1.compareTo(lap2)<0){
            if(lap1.compareTo(lap3)<0){
                p.bestLap=lap1;
            }
            else{
                p.bestLap=lap3;
            }
        }
        if(lap2.compareTo(lap1)<0){
            if(lap2.compareTo(lap3)<0){
                p.bestLap=lap2;
            }
            else{
                p.bestLap=lap3;
            }
        }
        return p;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getBestLap() {
        return bestLap;
    }
    public void setBestLap(String bestLap) {
        this.bestLap = bestLap;
    }

    @Override
    public int compareTo(Pilot o) {
        return this.bestLap.compareTo(o.bestLap);
    }
}
class F2Race{
    List<Pilot> piloti;

    public F2Race() {
        this.piloti = new ArrayList<>();
    }

    public void readResults(InputStream inputStream) {
        BufferedReader bf=new BufferedReader(new InputStreamReader(inputStream));
        piloti=bf.lines().map(line->Pilot.createPilot(line)).collect(Collectors.toList());
    }

    public void printSorted(PrintStream out) {
        Collections.sort(piloti);
        PrintWriter pw=new PrintWriter(out);
        int i=1;
        for (Pilot p:piloti){
            pw.println(String.format("%d. %-10s%10s",i, p.name, p.bestLap));
            i++;
        }
        pw.flush();
        pw.close();
    }
}
public class F2Test {

    public static void main(String[] args) throws FileNotFoundException {
        F2Race f2Race = new F2Race();
        File f=new File("C:\\Users\\toshiba\\IdeaProjects\\NaprednoProgramiranje\\src\\kolokviumski1\\F1Race2\\f2racefile.txt");
        f2Race.readResults(new FileInputStream(f));
        f2Race.printSorted(System.out);
    }

}