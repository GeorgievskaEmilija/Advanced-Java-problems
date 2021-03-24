package kolokviumski2code.Stadium;

import java.util.*;

class SeatNotAllowedException extends Exception {
}

class SeatTakenException extends Exception {

}

class Sector {
    String sectorCode;
    int maxseats;
    Map<Integer, Boolean> zafateniSeats;
    int type;


    public Sector(String sectorCode, int seats) {
        this.sectorCode = sectorCode;
        this.maxseats = seats;
        this.zafateniSeats = new HashMap<>();
        this.type = 10;

    }

    @Override
    public String toString() {
        //E 	1000/1000	0.0%
        float procent = (float)this.zafateniSeats.size() / maxseats;
        return String.format("%s\t%d/%d\t%.1f%%", sectorCode, maxseats-zafateniSeats.size(), maxseats, procent*100);
    }

    public String getSectorCode() {
        return sectorCode;
    }

    public void setSectorCode(String sectorCode) {
        this.sectorCode = sectorCode;
    }

    public int getMaxseats() {
        return maxseats;
    }

    public void setMaxseats(int maxseats) {
        this.maxseats = maxseats;
    }

    public Map<Integer, Boolean> getZafateniSeats() {
        return zafateniSeats;
    }

    public void setZafateniSeats(Map<Integer, Boolean> zafateniSeats) {
        this.zafateniSeats = zafateniSeats;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int freeSeats(){
        return maxseats-zafateniSeats.size();
    }

}

class Stadium {
    String name;
    Map<String, Sector> sectors;

    public Stadium(String name) {
        this.name = name;
        sectors = new HashMap<>();
    }

    void createSectors(String[] sectorNames, int[] sizes) {
        for (int i = 0; i < sectorNames.length; i++) {
            Sector sector = new Sector(sectorNames[i], sizes[i]);
            sectors.put(sector.sectorCode, sector);
        }
    }

    //sektorA/sediste1/tip1
    void buyTicket(String sectorName, int seat, int type) throws SeatNotAllowedException, SeatTakenException {
        Sector s = sectors.get(sectorName);
        if (s.type == 10 || s.type==0) { //prv pat se kupuva karta vo ovoj sektor ili e neutralen
            s.type = type;
        }
        if ((s.type==1 && type==2)||(s.type==2 && type==1) ) {
            throw new SeatNotAllowedException();
        } else {
            if (s.zafateniSeats.get(seat) == null) {
                s.zafateniSeats.put(seat, true);
            } else if (s.zafateniSeats.get(seat) != null && s.zafateniSeats.get(seat) == true) {
                throw new SeatTakenException();
            }
        }
    }

    public void showSectors() {
        sectors.values().stream()
                .sorted(Comparator.comparing(Sector::freeSeats).reversed()
                        .thenComparing(Sector::getSectorCode)).forEach(sector -> System.out.println(sector));
    }
}


public class StadiumTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] sectorNames = new String[n];
        int[] sectorSizes = new int[n];
        String name = scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            sectorNames[i] = parts[0];
            sectorSizes[i] = Integer.parseInt(parts[1]);
        }
        Stadium stadium = new Stadium(name);
        stadium.createSectors(sectorNames, sectorSizes);
        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            try {
                stadium.buyTicket(parts[0], Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]));
            } catch (SeatNotAllowedException e) {
                System.out.println("SeatNotAllowedException");
            } catch (SeatTakenException e) {
                System.out.println("SeatTakenException");
            }
        }
        stadium.showSectors();
    }
}
