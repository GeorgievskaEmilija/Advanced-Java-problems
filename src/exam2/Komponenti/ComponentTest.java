package kolokviumski2code.Komponenti;

import java.util.*;

class InvalidPositionException extends Exception {

    public InvalidPositionException(int position) {
        super(String.format("Invalid position %d, alredy taken!", position));
    }
}

class Component {
    String color;
    int weight;
    //TODO kolekcija od komponenti
    Set<Component> components;


    public Component(String color, int weight) {
        this.color = color;
        this.weight = weight;
        components = new TreeSet<>(Comparator.comparing(Component::getWeight).thenComparing(Component::getColor));
    }

    void addComponent(Component component) {
        components.add(component);
    }

    public String getColor() {
        return color;
    }

    public void changeColor(String color, int tezina){
        if (this.weight < tezina)
            this.color = color;

        for(Component c:components){
            c.changeColor(color,tezina);
        }
    }
    public void setColor(String color) {
        this.color = color;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }


    public String print(String tab) {
        StringBuilder sb=new StringBuilder();
        sb.append(tab+weight +":"+ color+"\n"); //80:YELLOW
        tab+="---";
        for(Component c:components){
            sb.append(c.print(tab));
        }
        return sb.toString();
    }
}

class Window {
    String name;
    Map<Integer, Component> components;

    public Window(String name) {
        this.name = name;
        this.components = new TreeMap<>();
    }

    void addComponent(int position, Component component) throws InvalidPositionException {
        if (components.get(position) != null) {
            throw new InvalidPositionException(position);
        } else components.put(position, component);
    }

    void changeColor(int weight, String color) {
        components.values().stream().forEach(c -> c.changeColor(color,weight));
    }

    void swichComponents(int pos1, int pos2) {
        Component c1 = components.get(pos1);
        Component c2 = components.get(pos2);
        components.remove(pos1);
        components.remove(pos2);

        components.put(pos1, c2);
        components.put(pos2, c1);
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append("WINDOW "+this.name+"\n");
        int i=1;
        for(Component c: components.values()){
            sb.append(i+":"+c.print(""));
            i++;
        }
        return sb.toString();
    }
}
public class ComponentTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        Window window = new Window(name);
        Component prev = null;
        while (true) {
            try {
                int what = scanner.nextInt();
                scanner.nextLine();
                if (what == 0) {
                    int position = scanner.nextInt();
                    window.addComponent(position, prev);
                } else if (what == 1) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev = component;
                } else if (what == 2) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev.addComponent(component);
                    prev = component;
                } else if (what == 3) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev.addComponent(component);
                } else if(what == 4) {
                    break;
                }

            } catch (InvalidPositionException e) {
                System.out.println(e.getMessage());
            }
            scanner.nextLine();
        }

        System.out.println("=== ORIGINAL WINDOW ===");
        System.out.println(window);
        int weight = scanner.nextInt();
        scanner.nextLine();
        String color = scanner.nextLine();
        window.changeColor(weight, color);
        System.out.println(String.format("=== CHANGED COLOR (%d, %s) ===", weight, color));
        System.out.println(window);
        int pos1 = scanner.nextInt();
        int pos2 = scanner.nextInt();
        System.out.println(String.format("=== SWITCHED COMPONENTS 1 <-> 2 ===", pos1, pos2));
        window.swichComponents(pos1, pos2);
        System.out.println(window);
    }
}

// вашиот код овде