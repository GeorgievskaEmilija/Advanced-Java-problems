package kolokviumski1.DDV;


import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class AmountNotAllowedException extends Exception {
    AmountNotAllowedException(int price) {
        super(String.format("Receipt with amount %d is not allowed to be scanned", price));
    }

}

class Item {
    String taxType;
    int price;

    public Item() {
    }

    public Item(int price, String taxType) {
        this.price = price;
        this.taxType = taxType;
    }

    public double ddv() {
        if (taxType.equals("A")) {
            return price * 0.18;
        } else if (taxType.equals("B")) {
            return price * 0.05;
        } else
            return 0;

    }
    public double taxReturn(){
        return ddv()*0.15;
    }

    public Item createItem(String s) {
        String[] parts = s.split("\\s+");
        return new Item(Integer.parseInt(parts[0]), parts[1]);
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

class FiskalnaSmetka {
    String id;
    List<Item> proizvodi = new ArrayList<>();


    public FiskalnaSmetka() {

    }

    public static FiskalnaSmetka createFiskalna(String line) throws AmountNotAllowedException {
        String[] parts = line.split("\\s+");
        FiskalnaSmetka fiskalnaSmetka = new FiskalnaSmetka();
        fiskalnaSmetka.id = parts[0];
        for (int i = 1; i < parts.length; i += 2) {
            fiskalnaSmetka.proizvodi.add(new Item(Integer.parseInt(parts[i]), parts[i + 1]));
        }
        if (fiskalnaSmetka.sumPrice() > 30000) {
            throw new AmountNotAllowedException(fiskalnaSmetka.sumPrice());
        }
        return fiskalnaSmetka;
    }

    public double getFiskalnaTax() {
        return proizvodi.stream().mapToDouble(i -> i.taxReturn()).sum();

    }

    public int sumPrice() {
        return proizvodi.stream().mapToInt(i -> i.getPrice()).sum();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Item> getProizvodi() {
        return proizvodi;
    }

    public void setProizvodi(List<Item> proizvodi) {
        this.proizvodi = proizvodi;
    }

    @Override
    public String toString() {
        return String.format("%10s\t%10d\t%10.5f", id, sumPrice(), getFiskalnaTax()*0.15);
    }
}

class MojDDV {
    List<FiskalnaSmetka> fiskalni;

    public void readRecords(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        fiskalni = br.lines().map(line -> {
            try {
                return FiskalnaSmetka.createFiskalna(line);
            } catch (AmountNotAllowedException e) {
                System.out.println(e.getMessage());
                return null;
            }
        })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

    }

    public void printTaxReturns(PrintStream out) {
        //“ID SUM_OF_AMOUNTS TAX_RETURN”
        PrintWriter pw = new PrintWriter(out);
        for (FiskalnaSmetka f : fiskalni)
            pw.println(f.toString());
        pw.flush();
    }

    public void printStatistics(OutputStream out) {
        PrintWriter pw=new PrintWriter(out);
        DoubleSummaryStatistics dss=fiskalni.stream()
                .mapToDouble(n->n.getFiskalnaTax())
                .summaryStatistics();
        pw.printf("min:\t%5.3f\nmax:\t%5.3f\nsum:\t%5.3f\ncount:%d\navg:\t%5.3f\n",
                dss.getMin(),
                dss.getMax(),
                dss.getSum(),
                dss.getCount(),
                dss.getAverage());

        pw.flush();
    }
}
public class MojDDVTest {
    public static void main(String[] args) throws FileNotFoundException {

        MojDDV mojDDV = new MojDDV();

        System.out.println("===READING RECORDS FROM INPUT STREAM===");
        File f = new File("C:\\Users\\toshiba\\IdeaProjects\\NaprednoProgramiranje\\src\\kolokviumski1\\DDV\\fiskalni.txt");
        mojDDV.readRecords(new FileInputStream(f));

        System.out.println("===PRINTING TAX RETURNS RECORDS TO OUTPUT STREAM ===");
        mojDDV.printTaxReturns(System.out);

        System.out.println("===PRINTING SUMMARY STATISTICS FOR TAX RETURNS TO OUTPUT STREAM===");
        mojDDV.printStatistics(System.out);

    }
}
