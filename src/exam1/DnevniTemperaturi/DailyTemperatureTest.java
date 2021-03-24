package kolokviumski1.DnevniTemperaturi;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

class Day implements Comparable<Day> {
    int day;
    List<Double> temperaturi;
    char scale;

    public Day(int day) {
        temperaturi = new ArrayList<>();
        this.day = day;
    }

    public static Day createDayFromLine(String line) {
        String[] parts = line.split("\\s+");
        Day day = new Day(Integer.parseInt(parts[0]));
        day.scale = parts[1].charAt(parts[1].length() - 1);
        for (int i = 1; i < parts.length; i++) {
            double value = Double.parseDouble(parts[i].substring(0, parts[i].length() - 1));
            day.temperaturi.add(value);
        }
        return day;
    }

    public String getDailyStatsC( char skala) {
        char currentSkala = this.scale;

        for (int i=0;i<this.temperaturi.size();i++) {
            if (currentSkala == 'F' && skala == 'C') {
                this.temperaturi.set(i, ((this.temperaturi.get(i) - 32) * 5.0 / 9));
            }
            if (currentSkala == 'C' && skala == 'F') {
                this.temperaturi.set(i, ((this.temperaturi.get(i) *9.0) /5 +32));

            }
            if (currentSkala == 'C' && skala == 'C') {
                continue;
            }
            if (currentSkala == 'F' && skala == 'F') {
                continue;
            }
        }


        DoubleSummaryStatistics dss = this.temperaturi.stream()
                .mapToDouble(t -> t.doubleValue())
                .summaryStatistics();
        return String.format("%3s: Count: %3d  Min: %3.2f Max: %3.2f Avg: %3.2f", this.day, dss.getCount(),
                dss.getMin(), dss.getMax(), dss.getAverage());

    }

    @Override
    public int compareTo(Day o) {
        return Integer.compare(this.day, o.day);
    }

    //137 23C 15C 28C

}

class DailyTemperatures {
    List<Day> dailyMeasurements;

    public DailyTemperatures() {
        dailyMeasurements = new ArrayList<>();
    }

    public void readTemperatures(InputStream in) {
        BufferedReader bf = new BufferedReader(new InputStreamReader(in));
        dailyMeasurements = bf.lines().map(line -> Day.createDayFromLine(line)).collect(Collectors.toList());
    }

    void writeDailyStats(OutputStream outputStream, char scale) {
        dailyMeasurements.sort(Comparator.naturalOrder());
        PrintWriter printWriter = new PrintWriter(outputStream);
        for (Day day : dailyMeasurements) {
            printWriter.println(day.getDailyStatsC(scale));
        }
        printWriter.flush();
    }
}

public class DailyTemperatureTest {
    public static void main(String[] args) throws FileNotFoundException {
        DailyTemperatures dailyTemperatures = new DailyTemperatures();
        File f = new File("C:\\Users\\toshiba\\IdeaProjects\\NaprednoProgramiranje\\src\\kolokviumski1\\DnevniTemperaturi\\temp.txt");
        dailyTemperatures.readTemperatures(new FileInputStream(f));
        System.out.println("=== Daily temperatures in Celsius (C) ===");
        dailyTemperatures.writeDailyStats(System.out, 'C');
        System.out.println("=== Daily temperatures in Fahrenheit (F) ===");
        DailyTemperatures copy=dailyTemperatures;
        copy.writeDailyStats(System.out, 'F');
    }
}

