package kolokviumski2code.CharacterCount;

import java.io.*;
import java.util.*;

interface CharacterCounter {
    Map<Character, Integer> readCharacters(InputStream inputStream) throws IOException;

    void printByCharacter(OutputStream outputStream);

    void printByFrequency(OutputStream outputStream);
}

class CharacterCounterImpl implements CharacterCounter {
    private Map<Character, Integer> map;

    CharacterCounterImpl() {
        this.map = new HashMap<>();
    }

    @Override
    public Map<Character, Integer> readCharacters(InputStream inputStream) throws IOException {
        // cita tekst od input stream i broi karakteri, koj karakter kolku pati se pojavuva.
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String text = br.readLine();
        for (Character c : text.toUpperCase().toCharArray()) {
            map.putIfAbsent(c, 0);
            map.put(c, map.get(c) + 1);
        }
        return map;
    }

    @Override
    public void printByCharacter(OutputStream outputStream) {
        PrintWriter pw = new PrintWriter(outputStream);
        map.entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .forEach(each -> pw.printf("%c : %d\n", each.getKey(), each.getValue()));
        pw.flush();

    }

    @Override
    public void printByFrequency(OutputStream outputStream) {
        PrintWriter pw = new PrintWriter(outputStream);
        Comparator<Map.Entry<Character, Integer>> comparator = (t, o) -> {
            if(t.getValue().equals(o.getValue()))
                return t.getKey() - o.getKey();
            return t.getValue() - o.getValue();
        };
        map.entrySet()
                .stream()
                .sorted(comparator)
                .forEach(each -> pw.printf("%d : %c\n", each.getValue(), each.getKey()));
        pw.flush();

    }
}

// SOLUTION:

public class CharacterCountTest {
    public static void main(String[] args) {
        CharacterCounter characterCounter = new CharacterCounterImpl();
        File f = new File("./input.txt");
        try {
            Map<Character, Integer> testMap = characterCounter.readCharacters(new FileInputStream(f));
            System.out.println("++++++++++++++++++ PRINTING BY KEY ++++++++++++++++++");
            characterCounter.printByCharacter(System.out);
            System.out.println("++++++++++++++++++ PRINTING BY VALUE ++++++++++++++++++");
            characterCounter.printByFrequency(System.out);
        } catch (IOException e) {
            System.out.println("++++++++++++++++++ ERROR WHILE READING FROM FILE ++++++++++++++++++");
        }
    }
}


