package av7;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class Name {
    String name;
    int counter;

    public Name(String name, int counter) {
        this.name = name;
        this.counter = counter;
    }

    public static Name createName(String line) {
        String[] parts = line.split("\\s+");
        return new Name(parts[0], Integer.parseInt(parts[1]));
    }
}

public class Names {
    private static Map<String, Integer> createMapfromFile(InputStream inputStream) {
        Map<String, Integer> result = new HashMap<>();
        BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
        bf.lines().map(line -> Name.createName(line))
                .forEach(ime -> result.put(ime.name, ime.counter));
        return result;
    }

    private static Map<String, Integer> findUnisexNames(Map<String, Integer> girlsMap, Map<String, Integer> boysMap) {
        Map<String, Integer> result = new HashMap<>();
        for (String name : girlsMap.keySet()) {
            if (boysMap.containsKey(name)) {
                result.put(name, (boysMap.get(name) + girlsMap.get(name)));
            }
        }
        return result;
    }

    public static void main(String[] args) {
        File girls = new File("C:\\Users\\toshiba\\IdeaProjects\\NaprednoProgramiranje\\src\\av7\\girlnames.txt");
        File boys = new File("C:\\Users\\toshiba\\IdeaProjects\\NaprednoProgramiranje\\src\\av7\\boynames.txt");
        try {

            Map<String, Integer> girlsMap = createMapfromFile(new FileInputStream(girls));
            Map<String, Integer> boysMap = createMapfromFile(new FileInputStream(boys));

            Map<String, Integer> result = findUnisexNames(girlsMap, boysMap);
            System.out.println(result);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
