package kolokviumski2code.MernaStanica;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

class Measurement {
    float temp;
    float wind;
    float humidity;
    float visibility;
    Date date;

    public Measurement(float temp, float wind, float humidity, float visibility, Date date) {
        this.temp = temp;
        this.wind = wind;
        this.humidity = humidity;
        this.visibility = visibility;
        this.date = date;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getWind() {
        return wind;
    }

    public void setWind(float wind) {
        this.wind = wind;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getVisibility() {
        return visibility;
    }

    public void setVisibility(float visibility) {
        this.visibility = visibility;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        //24.6 80.2 km/h 28.7% 51.7 km Tue Dec 17 23:40:15 CET 2013
        return String.format("%.1f %.1f km/h %.1f%% %.1f km %s",temp,wind,humidity,visibility,date.toString());
    }
}

class WeatherStation {
    List<Measurement> merenja;
    int days;

    public WeatherStation(int days) {
        this.days = days;
        merenja = new ArrayList<>(days);
    }

    public void addMeasurment(float temp, float wind, float hum, float vis, Date date) {
        Measurement ms = new Measurement(temp, wind, hum, vis, date);
        if(merenja.isEmpty()){
            merenja.add(ms);
            return;
        }
        for (Measurement m : merenja) {
            if (date.getTime() - m.getDate().getTime() <= 150000) {
                return;  //pred 2 ipol min ima drugo isto
            } else if (date.getTime() - m.getDate().getTime() >= this.days * 86400000) {
                //  сите мерења чие што време е постаро за x денови од новото се бришат
                merenja.remove(m);

            }
        }
        merenja.add(ms);

    }

    public int total() {
        return merenja.size();
    }
    public void status(Date from , Date to){
        List <Measurement> results= merenja.stream()
                .filter(m-> (m.getDate().after(from) && m.getDate().before(to)) || m.getDate().equals(from) ||m.getDate().equals(to))
                .sorted(Comparator.comparing(Measurement::getDate)).collect(Collectors.toList());

        results.stream().forEach(m-> System.out.println(m));

        float avg= 0;
        for(Measurement m:results){
            avg+=m.temp;
        }
        avg=avg/ results.size();
        System.out.println(String.format("Average temperature: %.2f",avg));
    }
}
public class WeatherStationTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        int n = scanner.nextInt();
        scanner.nextLine();
        WeatherStation ws = new WeatherStation(n);
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("=====")) {
                break;
            }
            String[] parts = line.split(" ");
            float temp = Float.parseFloat(parts[0]);
            float wind = Float.parseFloat(parts[1]);
            float hum = Float.parseFloat(parts[2]);
            float vis = Float.parseFloat(parts[3]);
            line = scanner.nextLine();
            Date date = df.parse(line);
            ws.addMeasurment(temp, wind, hum, vis, date);
        }
        String line = scanner.nextLine();
        Date from = df.parse(line);
        line = scanner.nextLine();
        Date to = df.parse(line);
        scanner.close();
        System.out.println(ws.total());
        try {
            ws.status(from, to);
        } catch (RuntimeException e) {
            System.out.println(e);
        }
    }
}
