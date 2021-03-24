package kolokviumski1.StackedCanvas;

import java.util.Scanner;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

enum Color {
    RED, GREEN, BLUE
}

interface Scalable {
    void scale(float scaleFactor);
}

interface Stackable {
    float weight();
}

abstract class Shape implements Scalable, Stackable {
    String id;
    Color color;

    public Shape(String id, Color color) {
        this.id = id;
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setId(String id) {
        this.id = id;
    }
}

class Circle extends Shape {
    float radius;

    public Circle(String id, Color color, float radius) {
        super(id, color);
        this.radius = radius;
    }


    @Override
    public void scale(float scaleFactor) {
        this.radius = radius * scaleFactor;
    }

    @Override
    public float weight() {
        return (float) (Math.PI * radius * radius);
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public String toString() {
        return String.format("C: %-5s%-10s%10.2f\n", getId(), getColor().toString(), weight());
    }
}

class Rectangle extends Shape {
    float width;
    float height;

    public Rectangle(String id, Color color, float width, float height) {
        super(id, color);
        this.width = width;
        this.height = height;
    }

    @Override
    public void scale(float scaleFactor) {
        this.width = width * scaleFactor;
        this.height = height * scaleFactor;

    }

    @Override
    public float weight() {
        return width * height;
    }

    @Override
    public String toString() {
        return String.format("R: %-5s%-10s%10.2f\n", getId(), getColor().toString(), weight());
    }
}

class Canvas {
    List<Shape> shapes;

    public Canvas() {
        shapes = new ArrayList<>();
    }

    void add(String id, Color color, float radius) {
        Shape shape = new Circle(id, color, radius);
        shapes.add(shape);
        shapes.sort(Comparator.comparing(Shape::weight).reversed());

    }

    void add(String id, Color color, float width, float height) {
        Shape shape = new Rectangle(id, color, width, height);
        shapes.add(shape);
        shapes.sort(Comparator.comparing(Shape::weight).reversed());
    }

    void scale(String id, float scaleFactor) {
        shapes.stream().filter(i -> i.getId().equals(id))
                .forEach(i -> i.scale(scaleFactor));
        shapes.sort(Comparator.comparing(Shape::weight).reversed());

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Shape s : shapes) {
            sb.append(s.toString());
        }
        return sb.toString();
    }
}


public class ShapesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Canvas canvas = new Canvas();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            int type = Integer.parseInt(parts[0]);
            String id = parts[1];
            if (type == 1) {
                Color color = Color.valueOf(parts[2]);
                float radius = Float.parseFloat(parts[3]);
                canvas.add(id, color, radius);
            } else if (type == 2) {
                Color color = Color.valueOf(parts[2]);
                float width = Float.parseFloat(parts[3]);
                float height = Float.parseFloat(parts[4]);
                canvas.add(id, color, width, height);
            } else if (type == 3) {
                float scaleFactor = Float.parseFloat(parts[2]);
                System.out.println("ORIGNAL:");
                System.out.print(canvas);
                canvas.scale(id, scaleFactor);
                System.out.printf("AFTER SCALING: %s %.2f\n", id, scaleFactor);
                System.out.print(canvas);
            }

        }
    }
}

