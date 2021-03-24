package av6;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ArrangeLetters {
    public static String arrange(String input) {

        //delumno iterativen nacin
        String[] words = input.split("\\s+");
        for (int i = 0; i < words.length; i++) { //
            char[] tmp = words[i].toCharArray();
            Arrays.sort(tmp);
            words[i] = new String(tmp);
        }
        return Arrays.stream(words)
                .sorted()
                .collect(Collectors.joining(" "));
    }

    public static String arrangeFunctional1(String input) {
        String[] parts = input.split("\\s+");

        return IntStream.range(0, parts.length)
                .mapToObj(i -> {
                    char[] tmp = parts[i].toCharArray();
                    Arrays.sort(tmp);
                    return new String(tmp);
                }).sorted().collect(Collectors.joining(" "));
    }


    public static String arrangeEmi(String input) {
        String[] parts = input.split("\\s+");
        return Arrays.stream(parts).map(s -> {
            char[] tmp = s.toCharArray();
            Arrays.sort(tmp);
            return new String(tmp);
        }).sorted().collect(Collectors.joining(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = "kO pSk sO";

        System.out.println(arrange(input));
        System.out.println(arrangeFunctional1(input));
        System.out.println(arrangeEmi(input));
    }


}
