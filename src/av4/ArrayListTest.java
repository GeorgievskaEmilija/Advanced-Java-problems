package av4;

import java.util.ArrayList;
import java.util.List;

public class ArrayListTest {
    public static void main(String[] args) {
        List<String> list=new ArrayList();
        list.add("Ime, tatkovoime, prezime");
        list.add("E");
        list.add("nes");
        list.stream()
                .map(string->string.length())
                .forEach(length-> System.out.println(length));

    }
}
