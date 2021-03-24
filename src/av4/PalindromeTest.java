package av4;

import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PalindromeTest {
    public static List<String> readWords(InputStream inputstream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputstream));
        //return br.lines().collect(Collectors.toList());
        return br.lines().map(word -> word.toLowerCase())
                .filter(word -> isPalindromeStream(word))
                .collect(Collectors.toList());

    }

    public static boolean isReversePalindrome(String word) {
        StringBuilder sb = new StringBuilder(word);
        String reverse = sb.reverse().toString();
        return word.equals(reverse);
    }

    public static boolean isPalindromeStream(String word) {
        return IntStream.range(0, word.length()).
                allMatch(i -> word.charAt(i) == word.charAt(word.length() - 1 - i));
    }

    public static void main(String[] args) throws FileNotFoundException {

        File f = new File("C:\\Users\\toshiba\\IdeaProjects\\NaprednoProgramiranje\\src\\av4\\words.txt");
        List<String> zborovi = readWords(new FileInputStream(f));
        String max = zborovi.stream().max(Comparator.comparingInt(i->i.length())).get();
        System.out.println(max);
    }
}
