package av6.benford;

import java.awt.print.PrinterGraphics;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BenfordLawTest {
    public static int[] counts(List<Integer> numbers) {
        int[] results = new int[10];
        for (Integer number : numbers) {
            int digit = findFirstDigit(number);
            results[digit]++;
        }
        return results;
    }



    public static int[] countsFunctional(List<Integer> numbers) {
        return numbers.stream()
                .map(BenfordLawTest::findFirstDigit)
                .map(x -> {
                    int[] result = new int[10];
                    result[x]++;
                    return result;
                }).reduce(new int[10], (left, right) -> {
                    Arrays.setAll(left, i -> left[i] + right[i]);
                    return left;
                });
    }


    static int findFirstDigit(int num) {
        while (num >= 10) {
            num /= 10;
        }
        return num;
    }

    public static void main(String[] args) {
        NumbersReader numbersReader = new LineNumbersReader();
        List<Integer> numbers = null;
        try {
            numbers = numbersReader.read(new FileInputStream("C:\\Users\\toshiba\\IdeaProjects\\NaprednoProgramiranje\\src\\av6\\benford\\test.txt"));
            CountVisualizer visualizer = new CountVisualizer(1);
            visualizer.visualize(System.out, counts(numbers));
            visualizer.visualize(System.out, countsFunctional(numbers));

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
