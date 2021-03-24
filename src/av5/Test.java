package av5;

import java.util.DoubleSummaryStatistics;
import java.util.*;

public class Test {
    public static void main(String[] args) {
        List<Integer> numbers=new ArrayList<>();
        for(int i=0;i<100;i++){
            numbers.add(i);
        }
//        DoubleSummaryStatistics dss= new DoubleSummaryStatistics();
//        numbers.forEach(n-> dss.accept(n.doubleValue()));
        DoubleSummaryStatistics dss=numbers.stream().mapToDouble(n->n.doubleValue()).summaryStatistics();
         System.out.println(dss.getSum());
    }
}
