package av6;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Finalists {
    public static List<Integer> pick1(int finalists, int awards) {
        List<Integer> result = new ArrayList<>();
        Random rand = new Random();
        int i = 3;
        while (i > 0) {
            int picked = rand.nextInt(finalists);
            if (result.contains(picked))
                continue;
            else {
                result.add(picked);
                i--;
            }
        }
        return result;
    }

    public static List<Integer> pick(int finalists, int awards) {
        Random random = new Random();
        int picked;
        List<Integer> awarded = new ArrayList<>();

        for (int i = 0; i < awards; i++) {
            picked = random.nextInt(finalists);
            if (!awarded.contains(picked)) {
                awarded.add(picked);
            }
        }
        return awarded;
    }


    public static void main(String[] args) {
        List<Integer> awarded = pick1(30, 3);
        System.out.println(awarded);
    }
}
