package av5;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class GenericBox<T> {
    List<T> elements;
    public static final Random RANDOM = new Random();

    public GenericBox() {
        this.elements = new ArrayList<T>();
    }

    public void addItem(T item) {
        this.elements.add(item);
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public T randomItem() {
        if (elements.isEmpty()) {
            return null;
        }
        return elements.remove(RANDOM.nextInt(elements.size()));
    }

}

public class BoxTest {
    public static void main(String[] args) {
        GenericBox<Integer> numbers = new GenericBox<>();
        for(int i=0;i<100;i++){
            numbers.addItem(i);
        }
        for(int i=0;i<10;i++){
            System.out.println(numbers.randomItem());

        }
    }
}
