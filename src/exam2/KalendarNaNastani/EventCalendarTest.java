package kolokviumski2code.KalendarNaNastani;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


class WrongDateException extends Exception {
    public WrongDateException(String date) {
        super(String.format("Wrong date: %s", date));
    }
}

class Event {
    String name;
    String location;
    Date date;

    public Event(String name, String location, Date date) {
        this.name = name;
        this.location = location;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long Gettime() {
        return this.date.getTime();
    }


    //15 Feb, 2013 19:30 at Expo Center, Stand Up
    @Override
    public String toString() {
        SimpleDateFormat df = new SimpleDateFormat("dd MMM, YYY HH:mm");
        String d = df.format(date);
        return String.format("%s at %s, %s",d, location, name);
    }
}

class EventCalendar {

    int year;
    Set<Event> events;
    int[] nastani;

    public EventCalendar(int year) {
        this.year = year;
        events = new TreeSet<>(Comparator.comparing(Event::Gettime).thenComparing(Event::getName));
        nastani=new int[12];
    }

    public void addEvent(String name, String location, Date date) throws WrongDateException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (cal.get(Calendar.YEAR) != this.year) {
            throw new WrongDateException(date.toString().replaceAll("GMT", "UTC"));
        }
        Event event = new Event(name, location, date);
        events.add(event);
        int index=date.getMonth();
        nastani[index]+=1;
    }

    public void listEvents(Date date) {
        List<Event> results= events.stream().filter(e -> e.date.getYear() == date.getYear()&&e.date.getMonth() == date.getMonth()&&e.date.getDay() == date.getDay()).collect(Collectors.toList());
        if(results.isEmpty()) {
            System.out.println("No events on this day!");
        } else {
            results.forEach(e -> System.out.println(e));
        }

    }
    public void listByMonth() {
        StringBuilder sb=new StringBuilder();
        for(int i=0; i<11; i++) {
            sb.append(String.format("%d : %d\n",i+1, nastani[i]));
        }
        sb.append(String.format("%d : %d",12, nastani[11]));
        System.out.println(sb.toString());
    }
}


public class EventCalendarTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        int year = scanner.nextInt();
        scanner.nextLine();
        EventCalendar eventCalendar = new EventCalendar(year);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            String name = parts[0];
            String location = parts[1];
            Date date = df.parse(parts[2]);
            try {
                eventCalendar.addEvent(name, location, date);
            } catch (WrongDateException e) {
                System.out.println(e.getMessage());
            }
        }
        Date date = df.parse(scanner.nextLine());
        eventCalendar.listEvents(date);
        eventCalendar.listByMonth();
    }
}
