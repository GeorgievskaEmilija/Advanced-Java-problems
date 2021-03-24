package kolokviumski1.Timetable;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

enum TimeFormat {
    FORMAT_24, FORMAT_AMPM

}

class InvalidTimeException extends Exception {
    public InvalidTimeException(String message) {
        super(message);
    }
}

class UnsupportedFormatException extends Exception {

    public UnsupportedFormatException(String message) {
        super(message);
    }
}

class Time implements Comparable<Time> {
    int hour;
    int minutes;

    public Time() {
    }

    public Time(int hour, int minutes) {

        this.hour = hour;
        this.minutes = minutes;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    @Override
    public int compareTo(Time o) {
        if (this.hour == o.hour)
            return Integer.compare(minutes, o.minutes);
        return Integer.compare(this.hour, o.hour);
    }

    @Override
    public String toString() {
        return String.format("%2d:%02d", hour, minutes);
    }
}

class TimeTable {
    List<Time> vreminja;

    public TimeTable() {
        vreminja = new ArrayList<>();
    }

    public void readTimes(InputStream in) throws IOException, UnsupportedFormatException, InvalidTimeException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(in));
        String line;
        while ((line = bf.readLine()) != null) {
            String[] parts = line.split("\\s+");
            for (int i = 0; i < parts.length; i++) {
                String time = parts[i];
                if (!time.contains(".") && !time.contains(":"))
                    throw new UnsupportedFormatException(time);

                time=time.replace(".", ":");
                int hour = Integer.parseInt(time.split(":")[0]);
                int minutes = Integer.parseInt(time.split(":")[1]);

                if ((hour < 0 && hour > 23) || (minutes < 0 && minutes > 59)) {
                    throw new InvalidTimeException(time);
                }
                vreminja.add(new Time(hour, minutes));
            }

        }
    }

    public void writeTimes(PrintStream out, TimeFormat format) {
        vreminja.sort(Comparator.naturalOrder());
        if (format == TimeFormat.FORMAT_AMPM) {
            Time pom = new Time();
            for (Time t : vreminja) {
                if (t.hour == 0) {
                    pom.hour = t.hour + 12;
                    pom.minutes = t.minutes;
                    System.out.println((pom.toString() + "AM"));
                }
                if (t.hour >= 1 && t.hour <= 11) {
                    System.out.println((t.toString() + "AM"));
                }
                if (t.hour == 12) {
                    System.out.println(t.toString() + "PM");
                }
                if (t.hour >= 13 && t.hour <= 23) {
                    pom.hour = t.hour - 12;
                    pom.minutes = t.minutes;
                    System.out.println(pom.toString() + "PM");
                }
            }
        } else if (format == TimeFormat.FORMAT_24) {
            for (Time t : vreminja) {
                System.out.println(t.toString());
            }
        }


    }
}

public class TimesTest {

    public static void main(String[] args) throws IOException {
        TimeTable timeTable = new TimeTable();
        try {
            timeTable.readTimes(System.in);
        } catch (UnsupportedFormatException e) {
            System.out.println("UnsupportedFormatException: " + e.getMessage());
        } catch (InvalidTimeException e) {
            System.out.println("InvalidTimeException: " + e.getMessage());
        }
        System.out.println("24 HOUR FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_24);
        System.out.println("AM/PM FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_AMPM);
    }

}

