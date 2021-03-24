package kolokviumski2code.UnikatniIminja;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


class Names {
    Map<String, Integer> mapa;

    public Names() {
        mapa = new HashMap<>();
    }

    public void addName(String name) {
        if (!mapa.containsKey(name)) {
            mapa.put(name, 1);
        } else {
            int val = mapa.get(name).intValue() + 1;
            mapa.put(name, val);
        }
    }


    public void printN(int n) {
        mapa.keySet().stream().filter(s -> mapa.get(s) >= n)
                .sorted()
                .forEach(name -> System.out.println(name + " (" + mapa.get(name) + ") " + findUniqueLetters(name)));
    }

    private int findUniqueLetters(String name) {
//        Set<Character> letters = new HashSet<>();
//        IntStream.range(0, name.length()).forEach(i -> letters.add(name.toLowerCase().charAt(i)));
//        return letters.size();
//
        List<Character> bukvi = new ArrayList<>();
        for (int i = 0; i < name.length(); i++) {
            if (!bukvi.contains(Character.toLowerCase(name.charAt(i)))) {
                bukvi.add(Character.toLowerCase(name.charAt(i)));
            } else {
                continue;
            }
        }
        return bukvi.size();
    }

    public String findName(int len, int index) {
        List<String> tmp = mapa.keySet().stream().filter(s -> s.length() < len).collect(Collectors.toList());
        if (index >= tmp.size())
            index = index % tmp.size();
        return tmp.get(index);

        // ArrayList<String> tmp = (ArrayList<String>) mapa.keySet().stream().filter(name -> name.length()<len).collect(Collectors.toList());
        //if (x >= tmp.size())
        // x = x%tmp.size();
        // return tmp.get(x);
    }
}

public class NamesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        Names names = new Names();
        for (int i = 0; i < n; ++i) {
            String name = scanner.nextLine();
            names.addName(name);
        }
        n = scanner.nextInt();
        System.out.printf("===== PRINT NAMES APPEARING AT LEAST %d TIMES =====\n", n);
        names.printN(n);
        System.out.println("===== FIND NAME =====");
        int len = scanner.nextInt();
        int index = scanner.nextInt();
        System.out.println(names.findName(len, index));
        scanner.close();

    }
}

// vashiot kod ovde