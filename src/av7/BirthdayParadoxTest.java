package av7;

import javax.swing.*;
import java.util.*;
import java.util.stream.IntStream;

public class BirthdayParadoxTest {


    public static boolean singleTrial(int persons) {
        Random random = new Random();
        HashSet<Integer> birthdays = new HashSet<>();
        for (int i = 0; i < persons; i++) {
            int bday = random.nextInt(364) + 1;
            if (birthdays.contains(bday)) {
                return true;
            }
            birthdays.add(bday);
        }
        return false;
    }

    public static double experiment(int person) {
        return IntStream.range(0, 5000)
                .filter(i -> singleTrial(person))
                .count() / 5000.0;

        //    int counter = 0;
//        for (int i=0;i<5000;i++) {
//            if (singleTrial(person))
//                ++counter;
//        }
//        return counter/5000.0;
        //pr: za 7 lugje se generira random rodenden -i
        // se proveruva dali se nasle dvajca so ist bday-
        // i ova se pravi  5000 pati
    }

    public static void main(String[] args) {
        for (int i = 2; i <= 50; i++) {
            System.out.printf("For %d people, the probability of two birthdays is about %.5f\n",
                    i,
                    experiment(i));
        }
    }

}
